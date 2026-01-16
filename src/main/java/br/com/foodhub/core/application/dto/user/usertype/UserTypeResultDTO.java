package br.com.foodhub.core.application.dto.user.usertype;

import br.com.foodhub.core.domain.entity.user.UserType;

public record UserTypeResultDTO(
        String id,
        String name,
        boolean restaurantRelated
) {
    public static UserTypeResultDTO from(UserType userType) {
        return new UserTypeResultDTO(
                userType.getId(),
                userType.getName(),
                userType.isRestaurantRelated()
        );
    }
}
