package br.com.foodhub.core.application.dto.menu;

import br.com.foodhub.core.application.dto.menu.items.MenuItemResultDTO;
import br.com.foodhub.core.domain.entity.menu.Menu;

import java.util.List;

public record MenuWithItemsResultDTO(
        String menuId,
        String name,
        List<MenuItemResultDTO> items
) {

    public static MenuWithItemsResultDTO from(Menu menu) {
        return new MenuWithItemsResultDTO(
                menu.getId(),
                menu.getName(),
                menu.getItems().stream()
                        .map(MenuItemResultDTO::from)
                        .toList()
        );
    }
}
