package dev.garage474.mspagamento.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemVendaRequestDTO {
  private Integer produtoId;
  private int quantidade;


}
