package br.com.foodhub.core.application.dto.user;

import java.util.Map;

public record UpdateUserDTO (
        String name,
        String email,
        String phone,
        Map<String, Object> customFields
) {
}
