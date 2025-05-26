package dev.garage474.mspagamento.adapter.dto.pagseguro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagSeguroPhoneDTO {
    private String country = "55";
    private String area;
    private String number;
    private String type;
}
