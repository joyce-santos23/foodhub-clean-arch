package br.com.foodhub.core.application.dto.user;

import br.com.foodhub.core.application.dto.userrestaurant.UserRestaurantResultDTO;
import br.com.foodhub.core.domain.entity.user.User;

import java.util.List;
import java.util.Map;

public record UserResultDTO(
        String id,
        String name,
        String email,
        String phone,
        String cpf,
        String userTypeId,
        List<UserRestaurantResultDTO> restaurants
) {
    public static UserResultDTO from(User user) {

        List<UserRestaurantResultDTO> restaurants =
                user.getRestaurants() == null
                        ? List.of()
                        : user.getRestaurants().stream()
                        .map(r -> UserRestaurantResultDTO.from(user.getId(), r))
                        .toList();


        return new UserResultDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getCpf(),
                user.getUserType().getId(),
                restaurants
        );
    }
}

