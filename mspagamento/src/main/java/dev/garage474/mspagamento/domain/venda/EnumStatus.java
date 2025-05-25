package dev.garage474.mspagamento.domain.venda;

import lombok.Getter;

@Getter
public enum EnumStatus {
    PENDENTE_PAGAMENTO(0, "Pendente pagamento"),
    EM_ANDAMENTO(1, "Em Andamento"),
    FINALIZADO(2, "Finalizado"),
    CANCELADO(3, "Cancelado");

    private final int codigo;
    private final String descricao;

    EnumStatus(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}