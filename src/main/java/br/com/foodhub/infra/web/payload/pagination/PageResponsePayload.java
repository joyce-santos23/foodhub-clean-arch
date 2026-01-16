package br.com.foodhub.infra.web.payload.pagination;

import java.util.List;

public record PageResponsePayload<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}

