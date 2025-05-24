package dev.garage474.mspagamento.adapter.dto;

import java.util.List;

import dev.garage474.mspagamento.domain.venda.EnumFormaPagamento;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RealizaVendaRequestDTO extends BaseDTO {
    private Integer clienteId;
    private List<ItemVendaRequestDTO> items;
    private EnumFormaPagamento formaPagamento;
    
}
