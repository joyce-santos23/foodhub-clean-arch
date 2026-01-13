package br.com.foodhub.core.application.dto.user;

import java.util.Map;

public record UserRequestDTO(
        String name,
        String email,
        String phone,
        String cpf,
        String password,
        String userTypeId,
        Map<String, Object> customFields
) {
}
