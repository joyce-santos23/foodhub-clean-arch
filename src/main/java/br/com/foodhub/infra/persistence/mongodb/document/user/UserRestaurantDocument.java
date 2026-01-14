package br.com.foodhub.infra.persistence.mongodb.document.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserRestaurantDocument {

    private String id;
    private String restaurantId;
    private String userTypeId;
}

