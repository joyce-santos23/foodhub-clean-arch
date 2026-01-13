package br.com.foodhub.core.application.port.restaurant;

import br.com.foodhub.core.application.dto.pagination.PageRequestDTO;
import br.com.foodhub.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;

import java.util.Optional;

public interface RestaurantGateway {
    Restaurant save(Restaurant restaurant);

    PageResultDTO<Restaurant> findAll(PageRequestDTO dto);

    Optional<Restaurant> findById(String restaurantId);
}
