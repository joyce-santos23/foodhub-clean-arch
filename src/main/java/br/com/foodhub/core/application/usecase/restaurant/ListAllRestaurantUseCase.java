package br.com.foodhub.core.application.usecase.restaurant;

import br.com.foodhub.core.application.dto.pagination.PageRequestDTO;
import br.com.foodhub.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListAllRestaurantUseCase {

    private final RestaurantGateway gateway;

    public PageResultDTO<RestaurantResultDTO> execute(PageRequestDTO dto) {
        PageResultDTO<Restaurant> page = gateway.findAll(dto);

        return new PageResultDTO<>(
                page.content().stream()
                        .map(restaurant -> new RestaurantResultDTO(
                                restaurant.getId(),
                                restaurant.getBusinessName(),
                                restaurant.getCnpj(),
                                restaurant.getCuisineType(),
                                restaurant.getOwnerId(),
                                restaurant.getAddressBaseId(),
                                restaurant.getNumberStreet(),
                                restaurant.getComplement(),
                                restaurant.getOpeningHours()
                        ))
                        .toList(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages()
        );
    }
}
