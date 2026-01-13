package br.com.foodhub.core.application.dto.user.usertype;

public record UserTypeResultDTO(
        String id,
        String name,
        boolean isRestaurantRelated
) {
}
