package dev.garage474.mspagamento.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.garage474.mspagamento.adapter.dto.ClienteDTO;
import dev.garage474.mspagamento.adapter.dto.VendaDTO;
import dev.garage474.mspagamento.adapter.dto.pagseguro.PagSeguroOrderDTO;
import dev.garage474.mspagamento.application.ports.output.PagSeguroGateway;
import dev.garage474.mspagamento.application.ports.output.QueueGateway;
import dev.garage474.mspagamento.application.ports.output.VendaRepository;
import dev.garage474.mspagamento.application.usecase.IniciaPagamentoUseCase;
import dev.garage474.mspagamento.domain.venda.EnumFormaPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class IniciaPagamentoUseCaseTest extends BaseUseCaseTest {

    @Mock
    private PagSeguroGateway pagSeguroGateway;

    @Mock
    private QueueGateway queueGateway;

    @Mock
    private VendaRepository vendaRepository;

    private IniciaPagamentoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new IniciaPagamentoUseCase(pagSeguroGateway, queueGateway, vendaRepository);
    }

    @Test
    void deveProcessarPagamentoComSucesso() throws Exception {
        // Arrange
        String receiptHandle = "receipt123";
        VendaDTO vendaDTO = criarVendaDTO();
        String vendaJson = new ObjectMapper().writeValueAsString(vendaDTO);
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put(receiptHandle, vendaJson);

        when(queueGateway.recebeMensagem()).thenReturn(messageMap);
        when(pagSeguroGateway.processarPagamento(any(PagSeguroOrderDTO.class))).thenReturn(MOCK_TRANSACTION_ID);

        // Act
        useCase.executa();

        // Assert
        verify(pagSeguroGateway).processarPagamento(any(PagSeguroOrderDTO.class));
        verify(vendaRepository).preencheNumTransacao(eq(1), eq(MOCK_TRANSACTION_ID));
        verify(queueGateway).apagaMensagemProcessada(eq(receiptHandle));
    }

    @Test
    void deveIgnorarQuandoNaoHaMensagensNaFila() {
        // Arrange
        when(queueGateway.recebeMensagem()).thenReturn(Collections.emptyMap());

        // Act
        useCase.executa();

        // Assert
        verify(pagSeguroGateway, never()).processarPagamento(any());
        verify(vendaRepository, never()).preencheNumTransacao(any(), any());
        verify(queueGateway, never()).apagaMensagemProcessada(any());
    }

    private VendaDTO criarVendaDTO() {
        return VendaDTO.builder()
                .id(1)
                .dataVenda(LocalDateTime.now())
                .formaPagamento(EnumFormaPagamento.CARTAO_CREDITO)
                .valorTotal(new BigDecimal("100.00"))
                .cliente(criarClienteDTO())
                .items(new ArrayList<>())
                .build();
    }

    private ClienteDTO criarClienteDTO() {
        return ClienteDTO.builder()
                .id(1)
                .nome("Test Customer")
                .cpfCnpj(MOCK_CUSTOMER_CPF)
                .email(MOCK_CUSTOMER_EMAIL)
                .telefone(MOCK_CUSTOMER_PHONE)
                .endereco("Test Address")
                .build();
    }
}
