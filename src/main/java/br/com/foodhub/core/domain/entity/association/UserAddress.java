package br.com.foodhub.core.domain.entity.association;

import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import lombok.Getter;

@Getter
public class UserAddress {
    private String id;
    private String userId;
    private String addressId;
    private String number;
    private String complement;
    private boolean primary;

    public UserAddress(
            String userId,
            String addressId,
            String number,
            String complement,
            boolean primary
    ) {
        this.userId = require(userId, "Usuário");
        this.addressId = require(addressId, "Endereço");
        this.number = require(number, "Número");
        this.complement = complement;
        this.primary = primary;
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
