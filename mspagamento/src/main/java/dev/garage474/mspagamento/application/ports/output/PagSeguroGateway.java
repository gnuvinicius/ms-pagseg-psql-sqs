package dev.garage474.mspagamento.application.ports.output;

import dev.garage474.mspagamento.adapter.dto.VendaDTO;

public interface PagSeguroGateway {
    String processarPagamento(VendaDTO venda);
}
