package br.com.foodhub.infra.web.payload.menu;

import br.com.foodhub.infra.web.payload.menu.items.MenuItemResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Menu com itens")
public record MenuWithItemsResponsePayload(

        @Schema(example = "69697bfb4a50530b33363161")
        String menuId,

        @Schema(example = "Cardápio Principal")
        String name,

        @Schema(description = "Itens do menu")
        List<MenuItemResponsePayload> items
) {}
