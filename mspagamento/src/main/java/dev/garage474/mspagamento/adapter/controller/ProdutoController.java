package dev.garage474.mspagamento.adapter.controller;

import dev.garage474.mspagamento.application.ports.output.ProdutoRepository;
import dev.garage474.mspagamento.domain.cadastro.Produto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loja")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/produtos")
    public ResponseEntity<?> getProdutos() {
        List<Produto> result = produtoRepository.listarProdutos();
        return ResponseEntity.ok(result);
    }
}
