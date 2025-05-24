package dev.garage474.mspagamento.adapter.controller;

import dev.garage474.mspagamento.adapter.dto.RealizaVendaRequestDTO;
import dev.garage474.mspagamento.adapter.dto.VendaDTO;
import dev.garage474.mspagamento.application.ports.output.VendaRepository;
import dev.garage474.mspagamento.application.usecase.EfetuaVendaUseCase;
import dev.garage474.mspagamento.domain.venda.Venda;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loja")
public class VendaController {

    private final EfetuaVendaUseCase efetuaVendaUseCase;
    private final VendaRepository vendaRepository;

    public VendaController(VendaRepository vendaRepository,
                           EfetuaVendaUseCase efetuaVendaUseCase) {
        this.vendaRepository = vendaRepository;
        this.efetuaVendaUseCase = efetuaVendaUseCase;
    }

    @GetMapping("/vendas")
    public ResponseEntity<?> getVendas() {
        List<Venda> vendas = vendaRepository.listarVendas();
        var result = vendas.stream().map(VendaDTO::fromEntity).toList();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/realiza-venda")
    public ResponseEntity<?> realizaVenda(@RequestBody RealizaVendaRequestDTO request) {

        try {
            efetuaVendaUseCase.setRequest(request);
            efetuaVendaUseCase.executa();
            return ResponseEntity.ok("Venda realizada com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
