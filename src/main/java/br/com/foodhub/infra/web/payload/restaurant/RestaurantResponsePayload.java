package br.com.foodhub.infra.web.payload.restaurant;

import br.com.foodhub.core.domain.entity.restaurant.OpeningHours;
import br.com.foodhub.infra.web.payload.address.AddressBaseResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Resposta com dados do restaurante")
public record RestaurantResponsePayload(

        @Schema(example = "69697bfb4a50530b33363161")
        String restaurantId,

        @Schema(example = "Pizzaria Napoli")
        String businessName,

        @Schema(example = "12.345.678/0001-90")
        String cnpj,

        @Schema(example = "Italiana")
        String cuisineType,

        @Schema(example = "user_456")
        String ownerId,


        @Schema(example = "450")
        String numberStreet,

        @Schema(example = "Loja 2")
        String complement,

        AddressBaseResponsePayload address,

        @Schema(description = "Horários de funcionamento do restaurante")
        List<OpeningHours> openingHours
) {}
