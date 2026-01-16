package br.com.foodhub.core.application.dto.address;

public record UpdateUserAddressDTO(
        String number,
        String complement,
        boolean primary
) {
}
