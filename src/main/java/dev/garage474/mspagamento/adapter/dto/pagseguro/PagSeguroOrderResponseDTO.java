package dev.garage474.mspagamento.adapter.dto.pagseguro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagSeguroOrderResponseDTO extends PagSeguroOrderDTO {
    private String id;

    @JsonProperty("created_at")
    private String createdAt;
}
