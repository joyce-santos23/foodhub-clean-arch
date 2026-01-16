package br.com.foodhub.core.application.usecase.userrestaurant;

import br.com.foodhub.core.application.dto.userrestaurant.LinkUserToRestaurantDTO;
import br.com.foodhub.core.application.dto.userrestaurant.UserRestaurantResultDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.association.UserRestaurant;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkUserToRestaurantUseCase {

    private final UserGateway userGateway;
    private final RestaurantGateway restaurantGateway;
    private final UserTypeGateway userTypeGateway;

    public UserRestaurantResultDTO execute(String userId, LinkUserToRestaurantDTO dto) {

        User user = userGateway.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado")
                );

        Restaurant restaurant = restaurantGateway.findById(dto.restaurantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurante não encontrado")
                );

        UserType userType = userTypeGateway.findById(dto.userTypeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tipo de usuário não encontrado")
                );

        if (!userType.isRestaurantRelated()) {
            throw new BusinessRuleViolationException(
                    "Este tipo de usuário não pode ser vinculado a um restaurante."
            );
        }

        if (user.isLinkedToRestaurant(restaurant.getId())) {
            throw new BusinessRuleViolationException(
                    "Usuário já está vinculado a este restaurante."
            );
        }

        // cria o vínculo
        user.addRestaurant(
                new UserRestaurant(
                        user.getId(),
                        restaurant.getId(),
                        userType.getId()
                )
        );

        User savedUser = userGateway.save(user);

        UserRestaurant savedLink = savedUser.getRestaurants().stream()
                .filter(r -> r.getRestaurantId().equals(restaurant.getId()))
                .findFirst()
                .orElseThrow();

        return UserRestaurantResultDTO.from(user.getId(), savedLink);

    }
}

