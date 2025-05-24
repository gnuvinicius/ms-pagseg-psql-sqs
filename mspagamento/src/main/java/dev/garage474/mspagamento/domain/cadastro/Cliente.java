package dev.garage474.mspagamento.domain.cadastro;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_cliente")
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "cliente_seq")
  @SequenceGenerator(name = "cliente_seq",
    sequenceName = "cliente_seq", allocationSize = 1, initialValue = 1)
  private int id;

  private String nome;

  @Column(name = "cpf_cnpj")
  private String cpfCnpj;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "endereco_id")
  private Endereco endereco;
  
  private String telefone;
  
  private String email;
}
