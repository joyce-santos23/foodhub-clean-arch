package br.com.foodhub.core.application.usecase.menu;

import br.com.foodhub.core.application.dto.menu.MenuWithItemsResultDTO;
import br.com.foodhub.core.application.dto.menu.items.MenuItemResultDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.menu.Menu;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListRestaurantMenuUseCase {

    private final RestaurantGateway gateway;

    public List<MenuWithItemsResultDTO> execute(String restaurantId){

        Restaurant restaurant = gateway.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurante não encontrado com ID: " + restaurantId
                        )
                );

        return restaurant.getMenus().stream()
                .map(MenuWithItemsResultDTO::from)
                .toList();
    }
}
