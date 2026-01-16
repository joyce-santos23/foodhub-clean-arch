package br.com.foodhub.infra.web.api.user;

import br.com.foodhub.infra.web.payload.userrestaurant.LinkUserToRestaurantRequestPayload;
import br.com.foodhub.infra.web.payload.userrestaurant.UserRestaurantResponsePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/users")
public interface UserRestaurantApi {

    @Operation(
            summary = "Vincular usuário a restaurante",
            description = "Cria um vínculo entre um usuário e um restaurante com um tipo de usuário"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário vinculado ao restaurante",
                    content = @Content(
                            schema = @Schema(implementation = UserRestaurantResponsePayload.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário, restaurante ou tipo de usuário não encontrado"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Usuário já está vinculado ao restaurante"
            )
    })
    UserRestaurantResponsePayload link(
            @Parameter(description = "ID do usuário")
            @PathVariable String userId,

            @RequestBody LinkUserToRestaurantRequestPayload payload
    );

    @Operation(
            summary = "Desvincular usuário de restaurante",
            description = "Remove o vínculo entre um usuário e um restaurante"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário desvinculado do restaurante"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário ou restaurante não encontrado",
                    content = @Content
            )
    })
    void unlink(
            @Parameter(description = "ID do usuário")
            @PathVariable String userId,

            @Parameter(description = "ID do restaurante")
            @PathVariable String restaurantId
    );
}
