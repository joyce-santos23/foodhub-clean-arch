package br.com.foodhub.core.application.usecase.restaurant;

import br.com.foodhub.core.application.dto.restaurant.openingHour.ChangeOpeningHoursDTO;
import br.com.foodhub.core.application.dto.restaurant.openingHour.OpeningHoursDTO;
import br.com.foodhub.core.application.dto.restaurant.openingHour.ChangeOpeningHoursDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.restaurant.OpeningHours;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeOpeningHoursUseCase {

    private final RestaurantGateway restaurantGateway;

    public void execute(ChangeOpeningHoursDTO dto) {

        Restaurant restaurant = restaurantGateway.findById(dto.restaurantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurante não encontrado com ID: " + dto.restaurantId()
                        )
                );

        List<OpeningHours> hours = dto.openingHours().stream()
                .map(this::toDomain)
                .toList();

        restaurant.changeOpeningHours(hours);

        restaurantGateway.save(restaurant);
    }

    private OpeningHours toDomain(OpeningHoursDTO dto) {
        return new OpeningHours(
                dto.dayOfWeek(),
                dto.openTime(),
                dto.closeTime(),
                dto.closed()
        );
    }
}
