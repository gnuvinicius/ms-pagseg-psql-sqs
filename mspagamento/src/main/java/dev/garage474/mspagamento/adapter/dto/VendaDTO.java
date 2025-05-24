package dev.garage474.mspagamento.adapter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import dev.garage474.mspagamento.domain.venda.EnumFormaPagamento;
import dev.garage474.mspagamento.domain.venda.Venda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendaDTO extends BaseDTO {
    private Integer id;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private LocalDateTime dataVenda;

    private EnumFormaPagamento formaPagamento;
    private BigDecimal valorTotal;
    private ClienteDTO cliente;
    private List<ItemVendaResponseDTO> items;

    public static VendaDTO fromEntity(Venda venda) {
        return VendaDTO.builder()
                .id(venda.getId())
                .dataVenda(venda.getDataVenda())
                .cliente(ClienteDTO.fromEntity(venda.getCliente()))
                .valorTotal(venda.getValorTotal())
                .formaPagamento(venda.getFormaPagamento())
                .items(ItemVendaResponseDTO.listFromEntity(venda.getItensVenda()))
                .build();
    }

    @Override
    public String toString() {
        return "VendaDTO{" +
                "id=" + id +
                ", dataVenda=" + dataVenda +
                ", formaPagamento=" + formaPagamento +
                ", valorTotal=" + valorTotal +
                ", cliente=" + cliente +
                ", items=" + items +
                '}';
    }
}
