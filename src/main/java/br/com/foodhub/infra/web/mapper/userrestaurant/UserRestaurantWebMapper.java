package br.com.foodhub.infra.web.mapper.userrestaurant;

import br.com.foodhub.core.application.dto.userrestaurant.LinkUserToRestaurantDTO;
import br.com.foodhub.core.application.dto.userrestaurant.UserRestaurantResultDTO;
import br.com.foodhub.infra.web.payload.userrestaurant.LinkUserToRestaurantRequestPayload;
import br.com.foodhub.infra.web.payload.userrestaurant.UserRestaurantResponsePayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRestaurantWebMapper {

    LinkUserToRestaurantDTO toDto(
            LinkUserToRestaurantRequestPayload payload
    );

    UserRestaurantResponsePayload toResponse(
            UserRestaurantResultDTO dto
    );
}
