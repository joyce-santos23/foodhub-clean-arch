package br.com.foodhub.core.application.usecase.menu;

import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMenuUseCase {

    private final RestaurantGateway gateway;
    private final UserGateway userGateway;

    public void execute(String userId, String restaurantId, String menuId) {

        User user = userGateway.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Restaurant restaurant = gateway.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurante não encontrado com o ID: " + restaurantId
                ));

        user.ensureCanManageRestaurant(restaurant);

        restaurant.removeMenu(menuId);
        gateway.save(restaurant);
    }

}
