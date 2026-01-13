package br.com.foodhub.core.application.dto.address;

public record UserAddressDTO(
        String userId,
        String cep,
        String number,
        String complement,
        boolean primary
) {
}
