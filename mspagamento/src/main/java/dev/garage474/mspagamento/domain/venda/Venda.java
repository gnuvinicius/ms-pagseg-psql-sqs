package dev.garage474.mspagamento.domain.venda;

import dev.garage474.mspagamento.domain.BaseEntity;
import dev.garage474.mspagamento.domain.cadastro.Cliente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_venda")
public class Venda implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venda_seq")
    @SequenceGenerator(name = "venda_seq", sequenceName = "venda_seq", allocationSize = 1, initialValue = 1)
    private int id;

    @Setter
    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "data_venda")
    private LocalDateTime dataVenda;

    @Column(name = "forma_pagamento")
    @Enumerated(EnumType.STRING)
    private EnumFormaPagamento formaPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "venda", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private final List<ItemVenda> itensVenda = new ArrayList<>();

    @Column(name = "num_transacao")
    private String numTransacao;

    public Venda(Cliente cliente, EnumFormaPagamento formaPagamento) {
        this.cliente = cliente;
        this.dataVenda = LocalDateTime.now();
        this.formaPagamento = formaPagamento;
        this.valorTotal = BigDecimal.ZERO;
    }

    public void addItem(ItemVenda item) {
//        var item = new ItemVenda(this, produto, quantidade);
        // item.persist();
        this.itensVenda.add(item);
    }

    public void setNumTransacao(String numTransacao) {
        if (this.numTransacao != null) {
            throw new IllegalStateException("Número de transação já preenchido.");
        }
        this.numTransacao = numTransacao;
    }
}
