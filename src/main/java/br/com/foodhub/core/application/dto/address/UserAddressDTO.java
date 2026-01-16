package br.com.foodhub.core.application.dto.address;

public record UserAddressDTO(
        String cep,
        String number,
        String complement,
        boolean primary
) {
}
