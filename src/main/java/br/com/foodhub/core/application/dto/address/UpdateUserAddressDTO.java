package br.com.foodhub.core.application.dto.address;

public record UpdateUserAddressDTO(
        String userAddressId,
        String number,
        String complement,
        boolean primary
) {
}
