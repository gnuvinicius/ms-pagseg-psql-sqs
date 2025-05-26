package dev.garage474.mspagamento.adapter.dto.pagseguro;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.garage474.mspagamento.adapter.dto.ItemVendaResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PagSeguroItemOrderDTO {

    @JsonProperty("reference_id")
    private String referenceId;
    private String name;
    private int quantity;

    @JsonProperty("unit_amount")
    private int unitAmount;

    public PagSeguroItemOrderDTO(ItemVendaResponseDTO itemVenda) {
        this.referenceId = itemVenda.getId().toString();
        this.name = itemVenda.getProduto().getNome();
        this.quantity = itemVenda.getQuantidade();
        this.unitAmount = converteParaCentavos(itemVenda);
    }

    private static int converteParaCentavos(ItemVendaResponseDTO itemVenda) {
        return itemVenda.getProduto()
                .getPreco()
                .multiply(new BigDecimal(100))
                .intValueExact();
    }

}
