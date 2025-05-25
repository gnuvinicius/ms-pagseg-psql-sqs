package dev.garage474.mspagamento.adapter.dto;

import dev.garage474.mspagamento.domain.BaseEntity;

public interface BaseDTO<ENTITY extends BaseEntity> {

    BaseDTO<ENTITY> fromEntity(ENTITY entity);
}
