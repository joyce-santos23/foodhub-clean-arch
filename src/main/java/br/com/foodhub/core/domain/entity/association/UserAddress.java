package br.com.foodhub.core.domain.entity.association;

import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserAddress {
    private String id;
    private String userId;

    @EqualsAndHashCode.Include
    private String addressId;

    @EqualsAndHashCode.Include
    private String number;

    @EqualsAndHashCode.Include
    private String complement;

    private boolean primary;

    public UserAddress(
            String userId,
            String addressId,
            String number,
            String complement,
            boolean primary
    ) {
        this.id = UUID.randomUUID().toString();
        this.userId = require(userId, "Usuário");
        this.addressId = require(addressId, "Endereço");
        this.number = require(number, "Número");
        this.complement = complement;
        this.primary = primary;
    }

    private UserAddress(
            String id,
            String userId,
            String addressId,
            String number,
            String complement,
            boolean primary
    ) {
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
        this.number = number;
        this.complement = complement;
        this.primary = primary;
    }

    public static UserAddress reconstitute(
            String id,
            String userId,
            String addressId,
            String number,
            String complement,
            boolean primary
    ) {
        return new UserAddress(
                id, userId, addressId, number, complement, primary
        );
    }

    public void update(String number, String complement) {
        this.number = require(number, "Número");
        this.complement = complement;
    }


    public void markAsPrimary() {
        this.primary = true;
    }

    public void unmarkAsPrimary() {
        this.primary = false;
    }

    private String require(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new RequiredFieldException(field);
        }
        return value;
    }

}
