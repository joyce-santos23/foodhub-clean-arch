package br.com.foodhub.infra.web.payload.user.usertype;

import jakarta.validation.constraints.NotBlank;

public record UserTypeRequestPayload(

        @NotBlank
        String name
) {
}
