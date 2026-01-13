package br.com.foodhub.core.application.usecase.restaurant;

import br.com.foodhub.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.core.application.dto.restaurant.UpdateRestaurantDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateRestaurantUseCase {

    private final RestaurantGateway gateway;

    public RestaurantResultDTO execute(UpdateRestaurantDTO dto) {

        Restaurant restaurant = gateway.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurante não encontrado com o ID: " + dto.id()
                ));
        restaurant.updateBasicInfo(
                dto.businessName(),
                dto.cuisineType(),
                dto.numberStreet(),
                dto.complement()
        );

        gateway.save(restaurant);

        return new RestaurantResultDTO(
                restaurant.getId(),
                restaurant.getBusinessName(),
                restaurant.getCnpj(),
                restaurant.getCuisineType(),
                restaurant.getOwnerId(),
                restaurant.getAddressBaseId(),
                restaurant.getNumberStreet(),
                restaurant.getComplement(),
                restaurant.getOpeningHours()
        );
    }
}
