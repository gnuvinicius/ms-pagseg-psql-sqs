package dev.garage474.mspagamento.infraestructure.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.garage474.mspagamento.adapter.dto.VendaDTO;
import dev.garage474.mspagamento.adapter.dto.pagseguro.PagSeguroOrderDTO;
import dev.garage474.mspagamento.adapter.dto.pagseguro.PagSeguroOrderResponseDTO;
import dev.garage474.mspagamento.application.ports.output.PagSeguroGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PagSeguroGatewayImpl implements PagSeguroGateway {

    @Value("${pagseguro.url.orders}")
    private String URL_PAGSEGURO;
    private static final Logger log = LoggerFactory.getLogger(PagSeguroGatewayImpl.class);
    private WebClient webClient;
    private final ObjectMapper mapper;

    @Value("${pagseguro.token}")
    private String token;

    public PagSeguroGatewayImpl(ObjectMapper mapper) {
//        this.webClient = webClientBuilder.baseUrl(URL_PAGSEGURO).build();
        this.mapper = mapper;
    }

    private void buildWebClient() {
        this.webClient = WebClient.builder()
                .baseUrl(URL_PAGSEGURO)
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }

    @Override
    public String processarPagamento(VendaDTO venda) {
        buildWebClient();
        try {
            log.info("Venda retornanda com sucesso pelo SQS: {}", venda);

            var requestBody = new PagSeguroOrderDTO(venda);
            String requestBodyJson = this.mapper.writeValueAsString(requestBody);

            PagSeguroOrderResponseDTO resposta = webClient.post()
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestBodyJson))
                    .retrieve()
                    .bodyToMono(PagSeguroOrderResponseDTO.class)
                    .block();

            if (resposta != null) {
                log.info("Resposta do PagSeguro: {}", resposta.getStatusCode());
                return resposta.getId();
            }
        } catch (Exception e) {
            log.error("Erro ao processar pagamento: {}", e.getMessage());
            throw new RuntimeException("Erro ao processar pagamento: " + e.getMessage());
        }
        return null;
    }
}
