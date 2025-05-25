package dev.garage474.mspagamento.infraestructure.repository;

import java.util.List;
import java.util.Objects;

import dev.garage474.mspagamento.adapter.dto.VendaDTO;
import dev.garage474.mspagamento.domain.PaginateResult;
import dev.garage474.mspagamento.domain.venda.ItemVenda;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dev.garage474.mspagamento.application.ports.output.VendaRepository;
import dev.garage474.mspagamento.domain.venda.HistoricoStatusVenda;
import dev.garage474.mspagamento.domain.venda.Venda;

@Repository
public class VendaRepositoryImpl implements VendaRepository {

    public static final String NON_NULL = "Número de transação não pode ser nulo";

    @Autowired
    private EntityManager em;

    @Override
    public Venda findById(Integer id) {
        String jpql = "SELECT v FROM Venda v "
                + "LEFT JOIN FETCH v.cliente c "
                + "LEFT JOIN FETCH v.itensVenda i "
                + "LEFT JOIN FETCH i.produto p WHERE v.id = :id";
        return em.createQuery(jpql, Venda.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void salvaVenda(Venda venda) {
        em.persist(venda);
    }

    @Override
    public void salvaStatusVenda(HistoricoStatusVenda historico) {
        em.persist(historico);
    }

    @Override
    public PaginateResult<Venda, VendaDTO> listarVendas(int page, int size) {
        String jpql = "SELECT v FROM Venda v "
                + "LEFT JOIN FETCH v.cliente c "
                + "LEFT JOIN FETCH v.itensVenda i "
                + "LEFT JOIN FETCH i.produto p ORDER BY v.dataVenda DESC ";

        List<Venda> resultList = em.createQuery(jpql, Venda.class)
                .setFirstResult(page * size) // offset
                .setMaxResults(size) // limit
                .getResultList();

        return new PaginateResult<>(resultList,
                entity -> (VendaDTO) new VendaDTO().fromEntity(entity),
                page,
                size,
                countTotalVendas());
    }

    private long countTotalVendas() {
        String jpql = "SELECT COUNT(v) FROM Venda v";
        return em.createQuery(jpql, Long.class).getSingleResult();
    }

    @Override
    public void salvaItemVenda(ItemVenda itemVenda) {
        em.persist(itemVenda);
    }

    @Transactional
    @Override
    public void preencheNumTransacao(Integer id, String numTransacao) {
        if (Objects.isNull(numTransacao)) {
            throw new IllegalArgumentException(NON_NULL);
        }

        Venda byId = findById(id);
        byId.setNumTransacao(numTransacao);
        em.merge(byId);
    }
}
