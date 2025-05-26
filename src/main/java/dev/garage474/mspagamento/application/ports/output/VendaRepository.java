package dev.garage474.mspagamento.application.ports.output;

import java.util.List;

import dev.garage474.mspagamento.adapter.dto.VendaDTO;
import dev.garage474.mspagamento.domain.PaginateResult;
import dev.garage474.mspagamento.domain.venda.HistoricoStatusVenda;
import dev.garage474.mspagamento.domain.venda.ItemVenda;
import dev.garage474.mspagamento.domain.venda.Venda;

public interface VendaRepository {
    Venda findById(Integer id);

    void salvaVenda(Venda venda);

    void salvaStatusVenda(HistoricoStatusVenda historico);

    PaginateResult<Venda, VendaDTO> listarVendas(int page, int size);

    void salvaItemVenda(ItemVenda itemVenda);

    void preencheNumTransacao(Integer id, String numTransacao);
}
