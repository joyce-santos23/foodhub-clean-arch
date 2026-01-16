package br.com.foodhub.infra.web.payload.user.usertype;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserTypeResponsePayload(
        String id,

        @Schema(example = "OWNER")
        String name,

        @Schema(example = "true")
        boolean restaurantRelated
) {
}
