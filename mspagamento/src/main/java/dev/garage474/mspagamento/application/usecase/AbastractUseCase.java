package dev.garage474.mspagamento.application.usecase;

import dev.garage474.mspagamento.adapter.dto.BaseDTO;
import dev.garage474.mspagamento.domain.BaseEntity;
import lombok.Setter;

/**
 * Classe abstrata que representa um caso de uso na aplicação.
 */
public abstract class AbastractUseCase<DTO> {

    @Setter
    protected DTO request;

    /**
     * Metodo que executa a lógica de negócio do caso de uso.
     */
    protected abstract void executa();


}
