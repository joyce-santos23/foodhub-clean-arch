package br.com.foodhub.core.application.dto.address;

public record AddressBaseDTO(
        String cep,
        String street,
        String neighborhood,
        String city,
        String state,
        String country
) {

}
