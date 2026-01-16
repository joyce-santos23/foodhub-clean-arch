package br.com.foodhub.infra.web.payload.address;

public record AddressBaseResponsePayload(
        String cep,
        String street,
        String neighborhood,
        String city,
        String state,
        String country
) {
}
