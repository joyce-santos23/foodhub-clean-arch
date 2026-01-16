package br.com.foodhub.infra.web.payload.user;

import br.com.foodhub.infra.web.payload.userrestaurant.UserRestaurantResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Resposta com dados do usuário")
public record UserResponsePayload(
        String id,

        @Schema(example = "João da Silva")
        String name,

        @Schema(example = "joao.silva@email.com")
        String email,

        @Schema(example = "+55 11 99999-9999")
        String phone,

        @Schema(example = "123.456.789-00")
        String cpf,

        @Schema(example = "69697bfb4a50530b33363161")
        String userTypeId,

        List<UserRestaurantResponsePayload> restaurants
) {
}
