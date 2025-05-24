package dev.garage474.mspagamento.adapter.dto;

import java.math.BigDecimal;

import dev.garage474.mspagamento.domain.cadastro.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
    private int id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int quantidade;

    public static ProdutoDTO fromEntity(Produto produto) {
        return ProdutoDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .quantidade(produto.getQuantidade())
                .build();
    }
}
