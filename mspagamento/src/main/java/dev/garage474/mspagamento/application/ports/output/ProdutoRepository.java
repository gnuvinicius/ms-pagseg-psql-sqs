package dev.garage474.mspagamento.application.ports.output;

import java.util.List;

import dev.garage474.mspagamento.domain.cadastro.Produto;

public interface ProdutoRepository {
  Produto findById(Integer id);
  List<Produto> listarProdutos();
}
