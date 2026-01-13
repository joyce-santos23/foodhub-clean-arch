package br.com.foodhub.core.application.usecase.menu;

import br.com.foodhub.core.application.dto.menu.MenuDeleteDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMenuUseCase {

    private final RestaurantGateway gateway;

    public void  execute(MenuDeleteDTO dto) {

        Restaurant restaurant = gateway.findById(dto.restaurantId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurante não encontrado com o ID: " + dto.restaurantId()));
        restaurant.removeMenu(dto.menuId());
        gateway.save(restaurant);
    }
}
