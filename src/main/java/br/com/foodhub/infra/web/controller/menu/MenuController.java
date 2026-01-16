package br.com.foodhub.infra.web.controller.menu;

import br.com.foodhub.core.application.dto.menu.MenuRequestDTO;
import br.com.foodhub.core.application.usecase.menu.CreateMenuUseCase;
import br.com.foodhub.core.application.usecase.menu.DeleteMenuUseCase;
import br.com.foodhub.core.application.usecase.menu.ListRestaurantMenuUseCase;
import br.com.foodhub.infra.web.api.menu.MenuApi;
import br.com.foodhub.infra.web.mapper.menu.MenuWebMapper;
import br.com.foodhub.infra.web.payload.menu.CreateMenuRequestPayload;
import br.com.foodhub.infra.web.payload.menu.MenuResponsePayload;
import br.com.foodhub.infra.web.payload.menu.MenuWithItemsResponsePayload;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Restaurantes")
@RestController
@RequestMapping("api/v1/restaurants/{restaurantId}/menus")
@RequiredArgsConstructor
public class MenuController implements MenuApi {

    private final CreateMenuUseCase createMenuUseCase;
    private final DeleteMenuUseCase deleteMenuUseCase;
    private final ListRestaurantMenuUseCase listRestaurantMenuUseCase;
    private final MenuWebMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuResponsePayload create(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String restaurantId,
            @RequestBody @Valid CreateMenuRequestPayload payload
    ) {
        return mapper.toResponse(
                createMenuUseCase.execute(
                        userId,
                        restaurantId,
                        mapper.toCreateDto(payload)
                )
        );
    }

    @GetMapping
    public List<MenuWithItemsResponsePayload> list(
            @PathVariable String restaurantId
    ) {
        return listRestaurantMenuUseCase.execute(restaurantId).stream()
                .map(mapper::toWithItemsResponse)
                .toList();
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String restaurantId,
            @PathVariable String menuId
    ) {
        deleteMenuUseCase.execute(userId, restaurantId, menuId);
    }
}

