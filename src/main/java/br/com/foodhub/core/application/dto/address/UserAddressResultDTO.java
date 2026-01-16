package br.com.foodhub.core.application.dto.address;

public record UserAddressResultDTO(
        String id,
        String userId,
        String addressBaseId,
        boolean primary,
        String number,
        String complement
) {}
