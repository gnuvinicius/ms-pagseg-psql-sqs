package dev.garage474.mspagamento.domain.venda;

import java.math.BigDecimal;

import dev.garage474.mspagamento.domain.cadastro.Produto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_item_venda")
public class ItemVenda {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_venda_seq")
  @SequenceGenerator(name = "item_venda_seq", sequenceName = "item_venda_seq", allocationSize = 1)
  private int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "produto_id")
  private Produto produto;

  private int quantidade;

  @Column(name = "preco_unitario")
  private BigDecimal precoUnitario;

  @Column(name = "preco_total")
  private BigDecimal precoTotal;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "venda_id")
  private Venda venda;

  public ItemVenda(Venda venda, Produto produto, int quantidade) {
    this.venda = venda;
    this.produto = produto;
    this.quantidade = quantidade;
    this.precoUnitario = produto.getPreco();
    this.precoTotal = precoUnitario.multiply(new BigDecimal(quantidade));
  }

}
