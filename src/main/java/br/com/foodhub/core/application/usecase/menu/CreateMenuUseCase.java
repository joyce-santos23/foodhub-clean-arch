package br.com.foodhub.core.application.usecase.menu;

import br.com.foodhub.core.application.dto.menu.MenuRequestDTO;
import br.com.foodhub.core.application.dto.menu.MenuResultDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.menu.Menu;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMenuUseCase {

    private RestaurantGateway gateway;

    public MenuResultDTO execute(MenuRequestDTO dto) {

        Restaurant restaurant = gateway.findById(dto.restaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

        Menu menu = new Menu(dto.name());

        restaurant.addMenu(menu);
        gateway.save(restaurant);

        return new MenuResultDTO(
                menu.getId(),
                menu.getName()
        );
    }
}
