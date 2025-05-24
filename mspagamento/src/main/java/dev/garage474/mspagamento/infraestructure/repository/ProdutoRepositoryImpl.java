package dev.garage474.mspagamento.infraestructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import dev.garage474.mspagamento.application.ports.output.ProdutoRepository;
import dev.garage474.mspagamento.domain.cadastro.Produto;
import jakarta.persistence.EntityManager;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {

  EntityManager em;

  public ProdutoRepositoryImpl(EntityManager entityManager) {
    this.em = entityManager;
  }

  @Override
  public Produto findById(Integer id) {
    try {
      String jpql = "SELECT p FROM Produto p WHERE p.id = :id";
      return em.createQuery(jpql, Produto.class)
          .setParameter("id", id)
          .getSingleResult();
    } catch (Exception e) {
      throw new IllegalArgumentException("Produto não encontrado");
    }
  }

  @Override
  public List<Produto> listarProdutos() {
    try {
      String jpql = "SELECT p FROM Produto p";
      return em.createQuery(jpql, Produto.class)
          .getResultList();
    } catch (Exception e) {
      throw new IllegalArgumentException("Nenhum produto encontrado");
    }
  }

}
