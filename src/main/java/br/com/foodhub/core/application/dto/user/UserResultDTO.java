package br.com.foodhub.core.application.dto.user;

import java.util.Map;

public record UserResultDTO(
        String id,
        String name,
        String email,
        String phone,
        String cpf,
        String userTypeId,
        Map<String, Object> attributes
) {}

