package dev.garage474.mspagamento.infraestructure.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.garage474.mspagamento.adapter.dto.pagseguro.PagSeguroOrderDTO;
import dev.garage474.mspagamento.adapter.dto.pagseguro.PagSeguroOrderResponseDTO;
import dev.garage474.mspagamento.application.ports.output.PagSeguroGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PagSeguroGatewayImpl implements PagSeguroGateway {

    private static final Logger log = LoggerFactory.getLogger(PagSeguroGatewayImpl.class);

    @Value("${pagseguro.url.orders}")
    private String URL_PAGSEGURO;

    private WebClient webClient;

    @Autowired
    private ObjectMapper mapper;

    @Value("${pagseguro.token}")
    private String token;

    @Override
    public String processarPagamento(PagSeguroOrderDTO order) {
        buildWebClient();
        try {
            String requestBodyJson = this.mapper.writeValueAsString(order);

            PagSeguroOrderResponseDTO resposta = webClient.post()
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestBodyJson))
                    .retrieve()
                    .bodyToMono(PagSeguroOrderResponseDTO.class)
                    .block();

            if (resposta != null) {
                log.info("Resposta do PagSeguro: {}", resposta.getId());
                return resposta.getId();
            }
        } catch (Exception e) {
            log.error("Erro ao processar pagamento: {}", e.getMessage());
            throw new RuntimeException("Erro ao processar pagamento: " + e.getMessage());
        }
        return null;
    }

    private void buildWebClient() {
        this.webClient = WebClient.builder()
                .baseUrl(URL_PAGSEGURO)
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }
}
