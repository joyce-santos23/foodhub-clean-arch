package br.com.foodhub.core.application.usecase.restaurant.openinghour;

import br.com.foodhub.core.application.dto.restaurant.openingHour.UpdateOpeningHoursDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
@RequiredArgsConstructor
public class ChangeOpeningHoursUseCase {

    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;

    public void execute(String userId, String restaurantId, DayOfWeek dayOfWeek, UpdateOpeningHoursDTO dto) {

        User user = userGateway.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Restaurant restaurant = restaurantGateway.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

        user.ensureCanManageRestaurant(restaurant);

        restaurant.changeOpeningHours(
                dayOfWeek,
                dto.openingTime(),
                dto.closingTime(),
                dto.closed()
        );

        restaurantGateway.save(restaurant);
    }
}
