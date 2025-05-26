package dev.garage474.mspagamento.infraestructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import dev.garage474.mspagamento.application.ports.output.ClienteRepository;
import dev.garage474.mspagamento.domain.cadastro.Cliente;
import jakarta.persistence.EntityManager;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    EntityManager entityManager;

    public ClienteRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Cliente findById(Integer id) {
        try {
            String jpql = "SELECT c FROM Cliente c WHERE c.id = :id";
            return entityManager.createQuery(jpql, Cliente.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        String jpql = "SELECT c FROM Cliente c LEFT JOIN c.endereco e";
        return entityManager.createQuery(jpql, Cliente.class)
                .getResultList();
    }

}
