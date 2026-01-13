package br.com.foodhub.core.application.dto.address;

public record UserAddressResultDTO(
        String userAddress,
        String user,
        String addressBase,
        boolean primary,
        String number,
        String complement
) {}
