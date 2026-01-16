package br.com.foodhub.core.application.usecase.restaurant;

import br.com.foodhub.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListRestaurantByIdUseCase {

    private final RestaurantGateway gateway;

    public RestaurantResultDTO execute(String restaurantId) {

        Restaurant restaurant = gateway.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurante não encontrado com o ID: " + restaurantId));

        return RestaurantResultDTO.from(restaurant);

    }
}
