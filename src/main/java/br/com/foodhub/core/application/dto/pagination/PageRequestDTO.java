package br.com.foodhub.core.application.dto.pagination;

public record PageRequestDTO(
        int page,
        int size
) {
}
