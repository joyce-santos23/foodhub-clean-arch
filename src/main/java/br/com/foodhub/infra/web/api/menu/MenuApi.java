package br.com.foodhub.infra.web.api.menu;

import br.com.foodhub.infra.web.payload.menu.CreateMenuRequestPayload;
import br.com.foodhub.infra.web.payload.menu.MenuResponsePayload;
import br.com.foodhub.infra.web.payload.menu.MenuWithItemsResponsePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/restaurants/{restaurantId}/menus")
public interface MenuApi {

    /* =========================
       Criar menu
       ========================= */
    @Operation(summary = "Criar menu do restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Menu criado com sucesso")
    })
    @PostMapping
    MenuResponsePayload create(
            @Parameter(
                    description = "ID do usuário que executa a ação",
                    required = true,
                    in = ParameterIn.HEADER
            )
            @RequestHeader("X-User-Id") String userId,

            @Parameter(description = "ID do restaurante")
            @PathVariable String restaurantId,

            @RequestBody CreateMenuRequestPayload payload
    );

    /* =========================
       Listar menus
       ========================= */
    @Operation(summary = "Listar menus do restaurante")
    @GetMapping
    List<MenuWithItemsResponsePayload> list(
            @Parameter(description = "ID do restaurante")
            @PathVariable String restaurantId
    );

    /* =========================
       Excluir menu
       ========================= */
    @Operation(summary = "Excluir menu do restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Menu removido com sucesso")
    })
    @DeleteMapping("/{menuId}")
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
            @PathVariable String menuId
    );
}
