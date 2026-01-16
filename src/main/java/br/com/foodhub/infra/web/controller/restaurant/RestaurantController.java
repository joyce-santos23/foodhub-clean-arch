package br.com.foodhub.infra.web.controller.restaurant;

import br.com.foodhub.core.application.dto.pagination.PageRequestDTO;
import br.com.foodhub.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.core.application.port.address.AddressBaseGateway;
import br.com.foodhub.core.application.usecase.restaurant.*;
import br.com.foodhub.core.domain.entity.address.AddressBase;
import br.com.foodhub.infra.web.api.restaurant.RestaurantApi;
import br.com.foodhub.infra.web.mapper.restaurant.RestaurantWebMapper;
import br.com.foodhub.infra.web.payload.restaurant.RestaurantRequestPayload;
import br.com.foodhub.infra.web.payload.restaurant.RestaurantResponsePayload;
import br.com.foodhub.infra.web.payload.restaurant.UpdateRestaurantRequestPayload;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Restaurantes")
@RestController
@RequestMapping("api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController implements RestaurantApi {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;
    private final ListAllRestaurantUseCase listAllRestaurantUseCase;
    private final ListRestaurantByIdUseCase listRestaurantByIdUseCase;
    private final AddressBaseGateway addressGateway;
    private final RestaurantWebMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantResponsePayload create(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody @Valid RestaurantRequestPayload payload
    ) {
        RestaurantResultDTO result =
                createRestaurantUseCase.execute(userId, mapper.toCreateDto(payload));

        AddressBase address = addressGateway.findById(result.addressBaseId()).orElseThrow();
        return mapper.toResponse(result, address);
    }

    @PutMapping("/{restaurantId}")
    public RestaurantResponsePayload update(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String restaurantId,
            @RequestBody @Valid UpdateRestaurantRequestPayload payload
    ) {
        RestaurantResultDTO result =
                updateRestaurantUseCase.execute(
                        userId,
                        restaurantId,
                        mapper.toUpdateDto(payload)
                );

        AddressBase address = addressGateway.findById(result.addressBaseId()).orElseThrow();
        return mapper.toResponse(result, address);
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String restaurantId
    ) {
        deleteRestaurantUseCase.execute(userId, restaurantId);
    }

    @GetMapping("/{restaurantId}")
    public RestaurantResponsePayload getRestaurantById(
            @PathVariable String restaurantId
    ) {
        RestaurantResultDTO result = listRestaurantByIdUseCase.execute(restaurantId);
        AddressBase address = addressGateway.findById(result.addressBaseId()).orElseThrow();
        return mapper.toResponse(result, address);
    }

    @GetMapping
    public PageResultDTO<RestaurantResponsePayload> listAllRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResultDTO<RestaurantResultDTO> result =
                listAllRestaurantUseCase.execute(new PageRequestDTO(page, size));

        return new PageResultDTO<>(
                result.content().stream()
                        .map(r -> mapper.toResponse(
                                r,
                                addressGateway.findById(r.addressBaseId()).orElseThrow()
                        ))
                        .toList(),
                result.page(),
                result.size(),
                result.totalElements(),
                result.totalPages()
        );
    }
}
