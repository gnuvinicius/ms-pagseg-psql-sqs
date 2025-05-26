package dev.garage474.mspagamento.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.garage474.mspagamento.adapter.dto.ItemVendaRequestDTO;
import dev.garage474.mspagamento.adapter.dto.RealizaVendaRequestDTO;
import dev.garage474.mspagamento.adapter.dto.VendaDTO;
import dev.garage474.mspagamento.application.ports.output.ClienteRepository;
import dev.garage474.mspagamento.application.ports.output.ProdutoRepository;
import dev.garage474.mspagamento.application.ports.output.QueueGateway;
import dev.garage474.mspagamento.application.ports.output.VendaRepository;
import dev.garage474.mspagamento.application.usecase.EfetuaVendaUseCase;
import dev.garage474.mspagamento.domain.cadastro.Cliente;
import dev.garage474.mspagamento.domain.cadastro.Endereco;
import dev.garage474.mspagamento.domain.cadastro.Produto;
import dev.garage474.mspagamento.domain.venda.EnumFormaPagamento;
import dev.garage474.mspagamento.domain.venda.HistoricoStatusVenda;
import dev.garage474.mspagamento.domain.venda.Venda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EfetuaVendaUseCaseTest extends BaseUseCaseTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private QueueGateway queueGateway;

    @Mock
    private ObjectMapper mapper;

    private EfetuaVendaUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new EfetuaVendaUseCase(clienteRepository, vendaRepository, queueGateway, produtoRepository);
    }

    @Test
    void deveRealizarVendaComSucesso() throws Exception {
        // Arrange
        RealizaVendaRequestDTO request = criarRequestVenda();
        Cliente cliente = criarCliente();
        Produto produto = criarProduto();
        String vendaJson = "{\"id\":1,\"cliente\":{\"nome\":\"Test\"}}";

        when(clienteRepository.findById(1)).thenReturn(cliente);
        when(produtoRepository.findById(1)).thenReturn(produto);
        when(mapper.writeValueAsString(any(VendaDTO.class))).thenReturn(vendaJson);

        // Act
        useCase.setRequest(request);
        useCase.executa();

        // Assert
        verify(vendaRepository, times(2)).salvaVenda(any(Venda.class));
        verify(vendaRepository).salvaStatusVenda(any(HistoricoStatusVenda.class));
        verify(vendaRepository).salvaItemVenda(any());
        verify(queueGateway).enviarMensagem(anyString());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        // Arrange
        RealizaVendaRequestDTO request = criarRequestVenda();
        when(clienteRepository.findById(1)).thenThrow(new IllegalArgumentException("Cliente não encontrado"));

        // Act & Assert
        useCase.setRequest(request);
        assertThrows(IllegalArgumentException.class, () -> useCase.executa());
    }

    private RealizaVendaRequestDTO criarRequestVenda() {
        RealizaVendaRequestDTO request = new RealizaVendaRequestDTO();
        ItemVendaRequestDTO item = ItemVendaRequestDTO.builder()
                .produtoId(1)
                .quantidade(2)
                .build();

        // Using reflection to set private fields since we don't have setters
        try {
            var field = request.getClass().getDeclaredField("clienteId");
            field.setAccessible(true);
            field.set(request, 1);

            field = request.getClass().getDeclaredField("items");
            field.setAccessible(true);
            field.set(request, Arrays.asList(item));

            field = request.getClass().getDeclaredField("formaPagamento");
            field.setAccessible(true);
            field.set(request, EnumFormaPagamento.CARTAO_CREDITO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return request;
    }

    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        Endereco endereco = new Endereco();
        
        // Using reflection to set private fields since we don't have setters
        try {
            var field = cliente.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(cliente, 1);

            field = cliente.getClass().getDeclaredField("nome");
            field.setAccessible(true);
            field.set(cliente, "Test Customer");

            field = cliente.getClass().getDeclaredField("cpfCnpj");
            field.setAccessible(true);
            field.set(cliente, MOCK_CUSTOMER_CPF);

            field = cliente.getClass().getDeclaredField("email");
            field.setAccessible(true);
            field.set(cliente, MOCK_CUSTOMER_EMAIL);

            field = cliente.getClass().getDeclaredField("telefone");
            field.setAccessible(true);
            field.set(cliente, MOCK_CUSTOMER_PHONE);

            field = cliente.getClass().getDeclaredField("endereco");
            field.setAccessible(true);
            field.set(cliente, endereco);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return cliente;
    }

    private Produto criarProduto() {
        Produto produto = new Produto();
        
        // Using reflection to set private fields since we don't have setters
        try {
            var field = produto.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(produto, 1);

            field = produto.getClass().getDeclaredField("nome");
            field.setAccessible(true);
            field.set(produto, "Test Product");

            field = produto.getClass().getDeclaredField("descricao");
            field.setAccessible(true);
            field.set(produto, "Test Description");

            field = produto.getClass().getDeclaredField("preco");
            field.setAccessible(true);
            field.set(produto, new BigDecimal("100.00"));

            field = produto.getClass().getDeclaredField("quantidade");
            field.setAccessible(true);
            field.set(produto, 10);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return produto;
    }
}
