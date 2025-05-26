package dev.garage474.mspagamento.adapter.dto;

import dev.garage474.mspagamento.domain.venda.EnumFormaPagamento;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RealizaVendaRequestDTO {
    private Integer clienteId;
    private List<ItemVendaRequestDTO> items;
    private EnumFormaPagamento formaPagamento;
    
}
