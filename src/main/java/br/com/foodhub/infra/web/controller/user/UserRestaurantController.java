package br.com.foodhub.infra.web.controller.user;

import br.com.foodhub.core.application.dto.userrestaurant.LinkUserToRestaurantDTO;
import br.com.foodhub.core.application.dto.userrestaurant.UserRestaurantResultDTO;
import br.com.foodhub.core.application.usecase.userrestaurant.LinkUserToRestaurantUseCase;
import br.com.foodhub.core.application.usecase.userrestaurant.UnlinkUserFromRestaurantUseCase;
import br.com.foodhub.infra.web.api.user.UserRestaurantApi;
import br.com.foodhub.infra.web.mapper.userrestaurant.UserRestaurantWebMapper;
import br.com.foodhub.infra.web.payload.userrestaurant.LinkUserToRestaurantRequestPayload;
import br.com.foodhub.infra.web.payload.userrestaurant.UserRestaurantResponsePayload;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users")
@RestController
@RequestMapping("api/v1/users/{userId}/restaurants")
@RequiredArgsConstructor
public class UserRestaurantController implements UserRestaurantApi {

    private final LinkUserToRestaurantUseCase linkUserToRestaurantUseCase;
    private final UnlinkUserFromRestaurantUseCase unlinkUserFromRestaurantUseCase;
    private final UserRestaurantWebMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRestaurantResponsePayload link(
            @PathVariable String userId,
            @RequestBody @Valid LinkUserToRestaurantRequestPayload payload
            ) {
        LinkUserToRestaurantDTO dto = mapper.toDto(payload);
        UserRestaurantResultDTO result =
                linkUserToRestaurantUseCase.execute(userId, dto);
        return mapper.toResponse(result);
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlink(
            @PathVariable String userId,
            @PathVariable String restaurantId
    ) {
        unlinkUserFromRestaurantUseCase.execute(userId, restaurantId);
    }
}
