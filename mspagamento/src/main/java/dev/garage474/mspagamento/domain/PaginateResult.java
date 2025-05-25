package dev.garage474.mspagamento.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.garage474.mspagamento.adapter.dto.BaseDTO;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class PaginateResult<ENTITY extends BaseEntity, DTO extends BaseDTO<ENTITY>> {

    @JsonIgnore
    private final List<ENTITY> content;

    private final List<DTO> results;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public PaginateResult(List<ENTITY> content,
                          Function<ENTITY, DTO> mapper,
                          int page,
                          int size,
                          long totalElements) {

        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);

        this.results = content.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
