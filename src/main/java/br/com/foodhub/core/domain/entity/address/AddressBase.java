package br.com.foodhub.core.domain.entity.address;

import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import lombok.Getter;

@Getter
public class AddressBase {
    private String id;
    private String cep;
    private String street;
    private String neighborhood;
    private String city;
    private String state;
    private String country;

    public AddressBase(
            String cep,
            String street,
            String neighborhood,
            String city,
            String state,
            String country
    ) {
        this.cep = normalizeCep(require(cep, "CEP"));
        this.street = require(street, "Rua");
        this.neighborhood = require(neighborhood, "Bairro");
        this.city = require(city, "Cidade");
        this.state = require(state, "Estado");
        this.country = require(country, "País");
    }

    private AddressBase(
            String id,
            String cep,
            String street,
            String neighborhood,
            String city,
            String state,
            String country
    ) {
        this.id = id;
        this.cep = cep;
        this.street = street;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public static AddressBase reconstitute(
            String id,
            String cep,
            String street,
            String neighborhood,
            String city,
            String state,
            String country
    ) {
        return new AddressBase(
                id, cep, street, neighborhood, city, state, country
        );
    }

    private String normalizeCep(String cep) {
        return cep.replaceAll("\\D", "");
    }

    private String require(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new RequiredFieldException(field);
        }
        return value;
    }
}
