package br.com.foodhub.core.application.usecase.userrestaurant;

import br.com.foodhub.core.application.dto.userrestaurant.UnlinkUserFromRestaurantDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnlinkUserFromRestaurantUseCase {

    private final UserGateway userGateway;
    private final RestaurantGateway restaurantGateway;

    public void execute(UnlinkUserFromRestaurantDTO dto) {

        User user = userGateway.findById(dto.userId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado")
                );

        Restaurant restaurant = restaurantGateway.findById(dto.restaurantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurante não encontrado")
                );

        // 🔒 Regra: não pode remover o dono do restaurante
        if (restaurant.getOwnerId().equals(user.getId())) {
            throw new BusinessRuleViolationException(
                    "Não é possível remover o dono do restaurante"
            );
        }
        user.removeRestaurantLink(restaurant.getId());
        userGateway.save(user);
    }
}
