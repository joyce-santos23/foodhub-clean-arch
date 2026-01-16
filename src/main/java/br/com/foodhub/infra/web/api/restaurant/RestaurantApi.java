package br.com.foodhub.infra.web.api.restaurant;

import br.com.foodhub.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.infra.web.payload.restaurant.RestaurantRequestPayload;
import br.com.foodhub.infra.web.payload.restaurant.RestaurantResponsePayload;
import br.com.foodhub.infra.web.payload.restaurant.UpdateRestaurantRequestPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/restaurants")
public interface RestaurantApi {

    /* =========================
       Criar restaurante
       ========================= */
    @Operation(summary = "Criar restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante criado")
    })
    @PostMapping
    RestaurantResponsePayload create(
            @Parameter(
                    description = "ID do usuário que executa a ação",
                    required = true,
                    in = ParameterIn.HEADER
            )
            @RequestHeader("X-User-Id") String userId,

            @RequestBody RestaurantRequestPayload payload
    );

    /* =========================
       Atualizar restaurante
       ========================= */
    @Operation(summary = "Atualizar restaurante")
    @PutMapping("/{restaurantId}")
    RestaurantResponsePayload update(
            @Parameter(
                    description = "ID do usuário que executa a ação",
                    required = true,
                    in = ParameterIn.HEADER
            )
            @RequestHeader("X-User-Id") String userId,

            @Parameter(description = "ID do restaurante")
            @PathVariable String restaurantId,

            @RequestBody UpdateRestaurantRequestPayload payload
    );

    /* =========================
       Buscar restaurante por ID
       ========================= */
    @Operation(summary = "Buscar restaurante por ID")
    @GetMapping("/{restaurantId}")
    RestaurantResponsePayload getRestaurantById(
            @Parameter(description = "ID do restaurante")
            @PathVariable String restaurantId
    );

    /* =========================
       Listar restaurantes
       ========================= */
    @Operation(summary = "Listar restaurantes")
    @GetMapping
    PageResultDTO<RestaurantResponsePayload> listAllRestaurants(
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Quantidade de registros por página", example = "10")
            @RequestParam(defaultValue = "10") int size
    );

    /* =========================
       Excluir restaurante
       ========================= */
    @Operation(summary = "Excluir restaurante")
    @ApiResponse(responseCode = "204", description = "Restaurante removido")
    @DeleteMapping("/{restaurantId}")
    void delete(
            @Parameter(
                    description = "ID do usuário que executa a ação",
                    required = true,
                    in = ParameterIn.HEADER
            )
            @RequestHeader("X-User-Id") String userId,

            @Parameter(description = "ID do restaurante")
            @PathVariable String restaurantId
    );
}
