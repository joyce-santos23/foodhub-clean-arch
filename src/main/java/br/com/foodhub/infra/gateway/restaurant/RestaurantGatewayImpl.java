package br.com.foodhub.infra.gateway.restaurant;

import br.com.foodhub.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.infra.persistence.mongodb.mapper.restaurant.RestaurantMapper;
import br.com.foodhub.infra.persistence.mongodb.repository.restaurant.RestaurantMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestaurantGatewayImpl implements RestaurantGateway {

    private final RestaurantMongoRepository repository;
    private final RestaurantMapper mapper;

    @Override
    public Restaurant save(Restaurant restaurant) {
        var document = mapper.toDocument(restaurant);
        var savedDocument = repository.save(document);
        return mapper.toDomain(savedDocument);
    }

    @Override
    public PageResultDTO<Restaurant> findAll(int page, int size) {
        var pegeable = PageRequest.of(page, size);
        var pageResult = repository.findAll(pegeable);

        var content = pageResult.getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();

        return new PageResultDTO<>(
                content,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    @Override
    public Optional<Restaurant> findById(String restaurantId) {
        return repository.findById(restaurantId)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(String restaurantId) {
        repository.deleteById(restaurantId);
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return repository.existsByCnpj(cnpj);
    }
}
