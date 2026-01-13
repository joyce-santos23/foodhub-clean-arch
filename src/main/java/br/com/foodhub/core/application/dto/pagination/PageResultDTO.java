package br.com.foodhub.core.application.dto.pagination;

import java.util.List;

public record PageResultDTO<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}
