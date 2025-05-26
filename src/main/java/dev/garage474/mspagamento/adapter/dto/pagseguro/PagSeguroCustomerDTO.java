package dev.garage474.mspagamento.adapter.dto.pagseguro;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.garage474.mspagamento.adapter.dto.ClienteDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PagSeguroCustomerDTO {

    private String name;
    private String email;

    /**
     * CPF ou CNPJ
     */
    @JsonProperty("tax_id")
    private String taxId;

    private List<PagSeguroPhoneDTO> phones = new ArrayList<>();

    public PagSeguroCustomerDTO(ClienteDTO cliente) {
        this.name = cliente.getNome();
        this.email = cliente.getEmail();
        this.taxId = cliente.getCpfCnpj();
        this.phones.add(new PagSeguroPhoneDTO("55", "81", cliente.getTelefone(), "MOBILE"));
    }
}

