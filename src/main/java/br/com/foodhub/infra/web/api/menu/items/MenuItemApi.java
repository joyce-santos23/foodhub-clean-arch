package br.com.foodhub.infra.web.api.menu.items;

import br.com.foodhub.infra.web.payload.menu.items.MenuItemRequestPayload;
import br.com.foodhub.infra.web.payload.menu.items.MenuItemResponsePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/restaurants/{restaurantId}/menus/{menuId}/items")
public interface MenuItemApi {

    /* =========================
       Criar item de menu
       ========================= */
    @Operation(summary = "Criar item de menu")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item de menu criado com sucesso")
    })
    @PostMapping
    MenuItemResponsePayload create(
            @Parameter(
                    description = "ID do usuário que executa a ação",
                    required = true,
                    in = ParameterIn.HEADER
            )
            @RequestHeader("X-User-Id") String userId,

            @Parameter(description = "ID do restaurante")
            @PathVariable String restaurantId,

            @Parameter(description = "ID do menu")
            @PathVariable String menuId,

            @RequestBody MenuItemRequestPayload payload
    );

    /* =========================
       Atualizar item de menu
       ========================= */
    @Operation(summary = "Atualizar item de menu")
    @PutMapping("/{menuItemId}")
    MenuItemResponsePayload update(
            @Parameter(
                    description = "ID do usuário que executa a ação",
                    required = true,
                    in = ParameterIn.HEADER
            )
            @RequestHeader("X-User-Id") String userId,

            @Parameter(description = "ID do restaurante")
            @PathVariable String restaurantId,

            @Parameter(description = "ID do menu")
            @PathVariable String menuId,

            @Parameter(description = "ID do item de menu")
            @PathVariable String menuItemId,

            @RequestBody MenuItemRequestPayload payload
    );

    /* =========================
       Excluir item de menu
       ========================= */
    @Operation(summary = "Excluir item de menu")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item de menu removido com sucesso")
    })
    @DeleteMapping("/{menuItemId}")
    void delete(
            @Parameter(
                    description = "ID do usuário que executa a ação",
                    required = true,
                    in = ParameterIn.HEADER
            )
            @RequestHeader("X-User-Id") String userId,

            @Parameter(description = "ID do restaurante")
            @PathVariable String restaurantId,

            @Parameter(description = "ID do menu")
            @PathVariable String menuId,

            @Parameter(description = "ID do item de menu")
            @PathVariable String menuItemId
    );

    /* =========================
       Buscar item de menu por ID
       ========================= */
    @Operation(summary = "Buscar item de menu por ID")
    @GetMapping("/{menuItemId}")
    MenuItemResponsePayload getById(
            @Parameter(description = "ID do restaurante")
            @PathVariable String restaurantId,

            @Parameter(description = "ID do menu")
            @PathVariable String menuId,

            @Parameter(description = "ID do item de menu")
            @PathVariable String menuItemId
    );
}
