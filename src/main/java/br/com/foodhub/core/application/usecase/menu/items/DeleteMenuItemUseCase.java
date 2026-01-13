package br.com.foodhub.core.application.usecase.menu.items;

import br.com.foodhub.core.application.dto.menu.items.DeleteMenuItemDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.menu.Menu;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMenuItemUseCase {

    private final RestaurantGateway gateway;

    public void execute(DeleteMenuItemDTO dto) {

        Restaurant restaurant = gateway.findById(dto.restaurantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurante não encontrado com ID: " + dto.restaurantId()
                        )
                );

        Menu menu = restaurant.getMenuById(dto.menuId());
        menu.removeItem(dto.menuId());
        gateway.save(restaurant);
    }
}
