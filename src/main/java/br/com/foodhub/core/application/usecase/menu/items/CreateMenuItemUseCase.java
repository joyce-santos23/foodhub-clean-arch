package br.com.foodhub.core.application.usecase.menu.items;

import br.com.foodhub.core.application.dto.menu.items.MenuItemRequestDTO;
import br.com.foodhub.core.application.dto.menu.items.MenuItemResultDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.menu.Menu;
import br.com.foodhub.core.domain.entity.menu.MenuItem;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMenuItemUseCase {

    private final RestaurantGateway gateway;

    public MenuItemResultDTO execute(MenuItemRequestDTO dto) {

        Restaurant restaurant = gateway.findById(dto.restaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

        Menu menu = restaurant.getMenuById(dto.menuId());

        MenuItem item = new MenuItem(
                dto.name(),
                dto.description(),
                dto.price(),
                dto.inRestaurantOnly(),
                dto.photograph()
        );
        menu.addItem(item);
        gateway.save(restaurant);

        return  new MenuItemResultDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.isInRestaurantOnly(),
                item.getPhotograph()
        );
    }
}
