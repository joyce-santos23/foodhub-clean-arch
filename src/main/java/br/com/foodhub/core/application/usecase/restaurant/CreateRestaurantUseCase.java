package br.com.foodhub.core.application.usecase.restaurant;

import br.com.foodhub.core.application.dto.restaurant.RestaurantRequestDTO;
import br.com.foodhub.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.application.usecase.address.FindOrCreateAddressBaseUseCase;
import br.com.foodhub.core.domain.entity.address.AddressBase;
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
    private final FindOrCreateAddressBaseUseCase findOrCreateAddressBaseUseCase;

    public RestaurantResultDTO execute(String ownerId, RestaurantRequestDTO dto) {

        User user = userGateway.findById(ownerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado")
                );

        user.ensureCanCreateRestaurant();

        String normalizedCnpj = dto.cnpj().replaceAll("\\D", "");

        if (gateway.existsByCnpj(normalizedCnpj)) {
            throw new BusinessRuleViolationException(
                    "Já existe um restaurante cadastrado com este CNPJ"
            );
        }

        AddressBase address = findOrCreateAddressBaseUseCase.execute(dto.cep());

        Restaurant restaurant = new Restaurant(
                dto.businessName(),
                dto.cnpj(),
                dto.cuisineType(),
                ownerId,
                address.getId(),
                dto.numberStreet(),
                dto.complement(),
                List.of()
        );
        Restaurant saved = gateway.save(restaurant);
        return RestaurantResultDTO.from(saved);

    }
}
