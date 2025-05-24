package dev.garage474.mspagamento.adapter.dto;

import dev.garage474.mspagamento.domain.cadastro.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Integer id;
    private String cpfCnpj;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;

    public static ClienteDTO fromEntity(Cliente cliente) {
        return ClienteDTO.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpfCnpj(cliente.getCpfCnpj())
                .endereco(cliente.getEndereco().toString())
                .telefone(cliente.getTelefone())
                .email(cliente.getEmail())
                .build();
    }
}
