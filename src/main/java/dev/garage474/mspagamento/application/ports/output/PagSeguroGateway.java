package dev.garage474.mspagamento.application.ports.output;

import dev.garage474.mspagamento.adapter.dto.pagseguro.PagSeguroOrderDTO;

public interface PagSeguroGateway {
    String processarPagamento(PagSeguroOrderDTO order);
}
