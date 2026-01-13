package br.com.foodhub.core.application.dto.menu;

import br.com.foodhub.core.application.dto.menu.items.MenuItemResultDTO;

import java.util.List;

public record MenuWithItemsResultDTO(
        String menuId,
        String name,
        List<MenuItemResultDTO> items
) {}