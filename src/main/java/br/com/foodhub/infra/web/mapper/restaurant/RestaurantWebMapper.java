package br.com.foodhub.infra.web.mapper.restaurant;

import br.com.foodhub.core.application.dto.restaurant.RestaurantRequestDTO;
import br.com.foodhub.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.core.application.dto.restaurant.UpdateRestaurantDTO;
import br.com.foodhub.core.domain.entity.address.AddressBase;
import br.com.foodhub.infra.web.payload.address.AddressBaseResponsePayload;
import br.com.foodhub.infra.web.payload.restaurant.RestaurantRequestPayload;
import br.com.foodhub.infra.web.payload.restaurant.RestaurantResponsePayload;
import br.com.foodhub.infra.web.payload.restaurant.UpdateRestaurantRequestPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantWebMapper {

    RestaurantRequestDTO toCreateDto(RestaurantRequestPayload payload);

    UpdateRestaurantDTO toUpdateDto(UpdateRestaurantRequestPayload payload);

    @Mapping(target = "address", source = "address")
    RestaurantResponsePayload toResponse(
            RestaurantResultDTO dto,
            AddressBase address
    );

    AddressBaseResponsePayload toAddress(AddressBase address);
}
