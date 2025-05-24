package dev.garage474.mspagamento.application.ports.output;

import java.util.List;

import dev.garage474.mspagamento.domain.cadastro.Cliente;

public interface ClienteRepository {
  Cliente findById(Integer id);
  List<Cliente> listarClientes();
}
