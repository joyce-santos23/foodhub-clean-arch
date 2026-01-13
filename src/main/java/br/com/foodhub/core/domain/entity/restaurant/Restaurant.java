package br.com.foodhub.core.domain.entity.restaurant;

import br.com.foodhub.core.domain.entity.menu.Menu;
import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.DomainException;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import br.com.foodhub.core.domain.exceptions.restaurant.InvalidCnpjException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Restaurant {
    private String id;
    private String businessName;
    private String cnpj;
    private String cuisineType;
    private String ownerId;
    private String addressBaseId;
    private String numberStreet;
    private String complement;
    private List<OpeningHours> openingHours;
    private List<Menu> menus;
    private boolean active;

    public Restaurant(
            String businessName,
            String cnpj,
            String cuisineType,
            String ownerId,
            String addressBaseId,
            String numberStreet,
            String complement,
            List<OpeningHours> openingHours
    ) {
        this.businessName = require(businessName, "Nome do restaurante");
        this.cnpj = normalizeCnpj(require(cnpj, "CNPJ"));
        this.cuisineType = require(cuisineType, "Tipo de cozinha");
        this.ownerId = require(ownerId, "Dono do restaurante");
        this.addressBaseId = require(addressBaseId, "Endereço");
        this.numberStreet = require(numberStreet, "Número");
        this.complement = complement;
        this.openingHours = openingHours != null ? new ArrayList<>(openingHours) : new ArrayList<>();
        this.menus = new ArrayList<>();
        this.active = true;
    }

    /* =========================
       Comportamentos de domínio
       ========================= */

    public void updateBasicInfo(
            String businessName,
            String cuisineType,
            String numberStreet,
            String complement
    ) {
        this.businessName = require(businessName, "Nome do restaurante");
        this.cuisineType = require(cuisineType, "Tipo de cozinha");
        this.numberStreet = require(numberStreet, "Número");
        this.complement = complement;
    }

    public void deactivate() {
        if (!this.active) {
            throw new BusinessRuleViolationException(
                    "Restaurante já está desativado"
            );
        }
        this.active = false;
    }

    public void addMenu(Menu menu) {
        require(menu, "Menu");

        boolean exists = menus.stream()
                .anyMatch(m -> m.getName().equalsIgnoreCase(menu.getName()));

        if (exists) {
            throw new BusinessRuleViolationException("Já existe um menu com esse nome");
        }

        this.menus.add(menu);
    }

    public void removeMenu(String menuId) {

        boolean removed = menus.removeIf(menu -> menu.getId().equals(menuId));

        if (!removed) {
            throw new DomainException(
                    "Menu não pertence ao restaurante"
            );
        }
    }

    public List<Menu> getMenus() {
        return List.copyOf(menus);
    }

    public Menu getMenuById(String menuId) {
        return menus.stream()
                .filter(menu -> menu.getId().equals(menuId))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu não pertence ao restaurante")
                );
    }

    public void changeOpeningHours(List<OpeningHours> openingHours) {
        this.openingHours.clear();
        if (openingHours != null) {
            this.openingHours.addAll(openingHours);
        }
    }


    /* =========================
       Validações internas
       ========================= */

    private String normalizeCnpj(String cnpj) {
        String normalized = cnpj.replaceAll("\\D", "");
        if (normalized.length() != 14) {
            throw new InvalidCnpjException();
        }
        return normalized;
    }

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

}
