package dev.garage474.mspagamento.domain.cadastro;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tb_produto")
public class Produto {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "produto_seq")
  @SequenceGenerator(name = "produto_seq",
    sequenceName = "produto_seq", allocationSize = 1, initialValue = 1)
  private int id;
  
  private String nome;
  private String descricao;
  private BigDecimal preco;
  private int quantidade;

}
