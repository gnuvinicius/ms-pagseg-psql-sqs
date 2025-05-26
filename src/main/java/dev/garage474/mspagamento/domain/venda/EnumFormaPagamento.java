package dev.garage474.mspagamento.domain.venda;

import lombok.Getter;

@Getter
public enum EnumFormaPagamento {
    DINHEIRO(0, "Dinheiro"),
    CARTAO_CREDITO(1, "Cartão de Crédito"),
    CARTAO_DEBITO(2, "Cartão de Débito"),
    PIX(3, "Pix"),
    BOLETO(4, "Boleto");

    private final int codigo;
    private final String descricao;

    EnumFormaPagamento(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
