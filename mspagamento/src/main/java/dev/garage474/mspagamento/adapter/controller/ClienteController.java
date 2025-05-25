package dev.garage474.mspagamento.adapter.controller;

import dev.garage474.mspagamento.adapter.dto.ClienteDTO;
import dev.garage474.mspagamento.application.ports.output.ClienteRepository;
import dev.garage474.mspagamento.domain.cadastro.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loja")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    public ResponseEntity<?> getClientes() {
        List<Cliente> clientes = clienteRepository.listarClientes();
        var result = clientes.stream().map(ClienteDTO::fromEntity).toList();
        return ResponseEntity.ok(result);
    }
}
