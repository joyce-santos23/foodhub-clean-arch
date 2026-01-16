package br.com.foodhub.infra.persistence.mongodb.document.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRestaurantDocument {

    private String restaurantId;
    private String userTypeId;
}

