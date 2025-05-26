package dev.garage474.mspagamento.domain.venda;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_historico_status_venda")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class HistoricoStatusVenda {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historico_status_venda_seq")
  @SequenceGenerator(name = "historico_status_venda_seq", sequenceName = "historico_status_venda_seq", allocationSize = 1)
  private int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "venda_id")
  private Venda venda;

  @Enumerated(EnumType.STRING)
  private EnumStatus status;

  @Column(name = "criado_em")
  private LocalDateTime criadoEm;

  @Column(name = "atualizado_em")
  private LocalDateTime atualizadoEm;
}
