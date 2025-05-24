package dev.garage474.mspagamento.application.usecase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.garage474.mspagamento.adapter.dto.*;
import dev.garage474.mspagamento.application.ports.output.QueueGateway;
import org.springframework.stereotype.Component;

import dev.garage474.mspagamento.application.ports.output.ClienteRepository;
import dev.garage474.mspagamento.application.ports.output.ProdutoRepository;
import dev.garage474.mspagamento.application.ports.output.VendaRepository;
import dev.garage474.mspagamento.domain.cadastro.Cliente;
import dev.garage474.mspagamento.domain.cadastro.Produto;
import dev.garage474.mspagamento.domain.venda.EnumStatus;
import dev.garage474.mspagamento.domain.venda.HistoricoStatusVenda;
import dev.garage474.mspagamento.domain.venda.ItemVenda;
import dev.garage474.mspagamento.domain.venda.Venda;
import jakarta.transaction.Transactional;

@Component
public class EfetuaVendaUseCase extends AbastractUseCase<RealizaVendaRequestDTO> {

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final VendaRepository vendaRepository;
    private final QueueGateway queueGateway;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EfetuaVendaUseCase(ClienteRepository clienteRepository,
                              VendaRepository vendaRepository,
                              QueueGateway queueGateway,
                              ProdutoRepository produtoRepository) {
        this.clienteRepository = clienteRepository;
        this.vendaRepository = vendaRepository;
        this.queueGateway = queueGateway;
        this.produtoRepository = produtoRepository;
    }

    @Override
    @Transactional
    public void executa() {
        try {
            Cliente cliente = clienteRepository.findById(request.getClienteId());
            Venda venda = new Venda(cliente, request.getFormaPagamento());
            this.vendaRepository.salvaVenda(venda);
            salvaItemsVendaSelecaoCliente(venda);
            finalizaVenda(venda);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao realizar a venda: " + e.getMessage());
        }
    }

    private void salvaItemsVendaSelecaoCliente(Venda venda) {
        for (ItemVendaRequestDTO item : request.getItems()) {
            Produto produto = produtoRepository.findById(item.getProdutoId());

            var itemVenda = new ItemVenda(venda, produto, item.getQuantidade());
            vendaRepository.salvaItemVenda(itemVenda);
            venda.addItem(itemVenda);
        }
    }

    private void finalizaVenda(Venda venda) throws JsonProcessingException {
        venda.setValorTotal(venda.getItensVenda().stream()
                .map(ItemVenda::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        var historico = HistoricoStatusVenda.builder()
                .venda(venda)
                .status(EnumStatus.PENDENTE_PAGAMENTO)
                .criadoEm(LocalDateTime.now())
                .build();

        this.vendaRepository.salvaStatusVenda(historico);
        this.vendaRepository.salvaVenda(venda);

        enviaParaFilaProcessamento(venda);
    }

    private void enviaParaFilaProcessamento(Venda venda) throws JsonProcessingException {
        String vendaJson = objectMapper.writeValueAsString(VendaDTO.fromEntity(venda));
        queueGateway.enviarMensagem(vendaJson);
    }

}
