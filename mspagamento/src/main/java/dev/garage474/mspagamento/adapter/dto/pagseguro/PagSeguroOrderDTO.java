package dev.garage474.mspagamento.adapter.dto.pagseguro;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.garage474.mspagamento.adapter.dto.VendaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PagSeguroOrderDTO {

    @JsonProperty("reference_id")
    private String referenceId;

    private PagSeguroCustomerDTO customer;
    private List<PagSeguroItemOrderDTO> items;


    private PagSeguroShippingDTO shipping;
    private String billing;

    @JsonProperty("notification_urls")
    private String[] notificationUrls;

    public PagSeguroOrderDTO(VendaDTO venda) {
        this.referenceId = venda.getId().toString();
        this.customer = new PagSeguroCustomerDTO(venda.getCliente());
        this.items = venda.getItems().stream().map(PagSeguroItemOrderDTO::new).toList();
    }

}
