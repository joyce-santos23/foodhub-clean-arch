package br.com.foodhub.core.application.dto.address;

import br.com.foodhub.core.domain.entity.association.UserAddress;

public record UserAddressResultDTO(
        String id,
        String userId,
        String addressBaseId,
        boolean primary,
        String number,
        String complement
) {
    public static UserAddressResultDTO from(UserAddress address) {
        return new UserAddressResultDTO(
                address.getId(),
                address.getUserId(),
                address.getAddressId(),
                address.isPrimary(),
                address.getNumber(),
                address.getComplement()
        );
    }
}
