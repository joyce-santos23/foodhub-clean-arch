package br.com.foodhub.core.application.usecase.restaurant;

import br.com.foodhub.core.application.dto.restaurant.RestaurantRequestDTO;
import br.com.foodhub.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateRestaurantUseCase {

    private final RestaurantGateway gateway;
    private final UserGateway userGateway;

    public RestaurantResultDTO execute(RestaurantRequestDTO dto, String userId) {

        User user = userGateway.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado")
                );

        if (!user.getUserType().isOwner()) {
            throw new BusinessRuleViolationException(
                    "Apenas usuários OWNER podem criar restaurantes"
            );
        }

        Restaurant restaurant = new Restaurant(
                dto.businessName(),
                dto.cnpj(),
                dto.cuisineType(),
                user.getId(),
                dto.addressBaseId(),
                dto.numberStreet(),
                dto.complement(),
                List.of()
        );
        Restaurant saved = gateway.save(restaurant);

        return new RestaurantResultDTO(
                saved.getId(),
                saved.getBusinessName(),
                saved.getCnpj(),
                saved.getCuisineType(),
                saved.getOwnerId(),
                saved.getAddressBaseId(),
                saved.getNumberStreet(),
                saved.getComplement(),
                saved.getOpeningHours()
        );
    }
}
