package br.com.foodhub.infra.web.payload.user.usertype;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserTypeRequestPayload(

        @NotBlank
        @Schema(example = "GERENTE")
        String name
) {
}
