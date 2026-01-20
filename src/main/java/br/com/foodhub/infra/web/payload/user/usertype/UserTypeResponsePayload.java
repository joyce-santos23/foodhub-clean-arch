package br.com.foodhub.infra.web.payload.user.usertype;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserTypeResponsePayload(
        @Schema(example = "69697bfb4a50530b33363161")
        String id,

        @Schema(example = "OWNER")
        String name,

        @Schema(example = "true")
        boolean restaurantRelated
) {
}
