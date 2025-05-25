package dev.garage474.mspagamento.application.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.garage474.mspagamento.adapter.dto.VendaDTO;
import dev.garage474.mspagamento.adapter.dto.pagseguro.PagSeguroOrderDTO;
import dev.garage474.mspagamento.application.ports.output.PagSeguroGateway;
import dev.garage474.mspagamento.application.ports.output.QueueGateway;
import dev.garage474.mspagamento.application.ports.output.VendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class IniciaPagamentoUseCase extends AbastractUseCase<VendaDTO> {

    private static final Logger log = LoggerFactory.getLogger(IniciaPagamentoUseCase.class);
    public static final String JSON_PARA_VENDA_DTO = "SQS: Erro ao converter json para vendaDTO: %s";
    private final PagSeguroGateway pagSeguroGateway;
    private final QueueGateway queueGateway;
    private final VendaRepository vendaRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public IniciaPagamentoUseCase(PagSeguroGateway pagSeguroGateway,
                                  QueueGateway queueGateway,
                                  VendaRepository vendaRepository) {
        this.pagSeguroGateway = pagSeguroGateway;
        this.queueGateway = queueGateway;
        this.vendaRepository = vendaRepository;
    }

    @Scheduled(fixedDelay = 5000)
    @Override
    protected void executa() {
        queueGateway.recebeMensagem()
                .forEach((receiptHandle, message) -> {
                    processaVenda(message);
                    queueGateway.apagaMensagemProcessada(receiptHandle);
                });
    }

    private void processaVenda(String message) {
        try {
            VendaDTO venda = mapper.readValue(message, VendaDTO.class);
            PagSeguroOrderDTO order = new PagSeguroOrderDTO(venda);

            String numTransacao = pagSeguroGateway.processarPagamento(order);

            vendaRepository.preencheNumTransacao(venda.getId(), numTransacao);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(String.format(JSON_PARA_VENDA_DTO, e.getMessage()));
        }
    }
}
