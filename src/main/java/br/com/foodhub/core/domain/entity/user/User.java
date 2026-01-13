package br.com.foodhub.core.domain.entity.user;

import br.com.foodhub.core.domain.entity.association.UserAddress;
import br.com.foodhub.core.domain.entity.association.UserRestaurant;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceConflictException;
import br.com.foodhub.core.domain.exceptions.user.AddressNotBelongsToUserException;
import br.com.foodhub.core.domain.exceptions.user.InvalidCpfException;
import br.com.foodhub.core.domain.exceptions.user.InvalidEmailException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class User {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String cpf;
    private String password;
    private UserType userType;
    private UserProfile attributes;
    private List<UserAddress> addresses = new ArrayList<>();
    private List<UserRestaurant> restaurants = new ArrayList<>();


    public User(
            String name,
            String email,
            String phone,
            String password,
            UserType userType,
            UserProfile attributes
    ) {
        this.name = require(name, "Name");
        this.email = normalizeEmail(require(email, "Email"));
        this.phone = require(phone, "Phone");
        this.password = require(password, "Password");
        this.userType = require(userType, "User Type");
        this.attributes = attributes != null ? attributes : UserProfile.empty();
    }

    /* =========================
       Comportamentos de domínio
       ========================= */

    public void defineCpf(String cpf) {
        if (this.cpf != null) {
            throw new ResourceConflictException("Cpf já foi definido para este usuário.");
        }
        this.cpf = normalizeCpf(require(cpf, "CPF"));
    }

    public void ensureCanCreateRestaurant() {
        if (!userType.isOwner()) {
            throw new BusinessRuleViolationException(
                    "Apenas owners podem criar restaurantes"
            );
        }
    }

    public void ensureCanManageRestaurant(Restaurant restaurant) {

        if (userType.isCustomer()) {
            throw new BusinessRuleViolationException(
                    "Clientes não podem gerenciar restaurantes"
            );
        }

        if (userType.isOwner()) {
            if (!restaurant.getOwnerId().equals(this.id)) {
                throw new BusinessRuleViolationException(
                        "Usuário não é dono deste restaurante"
                );
            }
            return;
        }

        if (userType.isRestaurantRelated() && !isLinkedToRestaurant(restaurant.getId())) {
            throw new BusinessRuleViolationException(
                    "Usuário não está vinculado ao restaurante"
            );
        }
    }

    public void addRestaurant(UserRestaurant userRestaurant) {
        if (userRestaurant == null) {
            throw new RequiredFieldException("Vínculo não pode ser nulo");
        }
        this.restaurants.add(userRestaurant);
    }

    public boolean isLinkedToRestaurant(String restaurantId) {
        return restaurants.stream()
                .anyMatch(r -> r.getRestaurantId().equals(restaurantId));
    }

    public void removeRestaurantLink(String restaurantId) {

        boolean removed = restaurants.removeIf(
                r -> r.getRestaurantId().equals(restaurantId)
        );

        if (!removed) {
            throw new BusinessRuleViolationException(
                    "Usuário não está vinculado a este restaurante"
            );
        }
    }


    public void addAddress(UserAddress address) {

        require(address, "Endereço");

        if (address.isPrimary()) {
            unsetCurrentPrimary();
        }

        this.addresses.add(address);
    }

    public void definePrimaryAddress(String addressId) {

        boolean found = false;

        for (UserAddress address : addresses) {

            if (addressId.equals(address.getId())) {
                address.markAsPrimary();
                found = true;
            } else {
                address.unmarkAsPrimary();
            }
        }

        if (!found) {
            throw new AddressNotBelongsToUserException();
        }
    }

    private void unsetCurrentPrimary() {
        for (UserAddress address : addresses) {
            address.unmarkAsPrimary();
        }
    }

    public void changeName(String name) {
        this.name = require(name, "Nome");
    }

    public void changeEmail(String email) {
        this.email = normalizeEmail(require(email, "Email"));
    }

    public void changePhone(String phone) {
        this.phone = require(phone, "Telefone");
    }

    public void updateProfile(UserProfile profile) {
        this.attributes = Objects.requireNonNull(profile);
    }


    /* =========================
       Utilidades de validação
       ========================= */

    private String require(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new RequiredFieldException(field);
        }
        return value;
    }

    private <T> T require(T value, String field) {
        if (value == null) {
            throw new RequiredFieldException(field);
        }
        return value;
    }

    private String normalizeCpf(String cpf) {
        String normalized = cpf.replaceAll("\\D", "");

        if (normalized.length() != 11) {
            throw new InvalidCpfException("Cpf inválido");
        }

        return normalized;
    }

    private String normalizeEmail(String email) {
        String normalized = email.trim().toLowerCase();

        if (!normalized.matches(".+@.+\\..+")) {
            throw new InvalidEmailException("Email inválido");
        }

        return normalized;
    }

}
