package br.com.foodhub.infra.web.controller.menu.items;

import br.com.foodhub.core.application.usecase.menu.items.CreateMenuItemUseCase;
import br.com.foodhub.core.application.usecase.menu.items.DeleteMenuItemUseCase;
import br.com.foodhub.core.application.usecase.menu.items.ListMenuItemUseCase;
import br.com.foodhub.core.application.usecase.menu.items.UpdateMenuItemUseCase;
import br.com.foodhub.infra.web.api.menu.items.MenuItemApi;
import br.com.foodhub.infra.web.mapper.menu.items.MenuItemWebMapper;
import br.com.foodhub.infra.web.payload.menu.items.MenuItemRequestPayload;
import br.com.foodhub.infra.web.payload.menu.items.MenuItemResponsePayload;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Restaurantes")
@RestController
@RequestMapping("api/v1/restaurants/{restaurantId}/menus/{menuId}/items")
@RequiredArgsConstructor
public class MenuItemController implements MenuItemApi {

    private final CreateMenuItemUseCase createUseCase;
    private final UpdateMenuItemUseCase updateUseCase;
    private final DeleteMenuItemUseCase deleteUseCase;
    private final ListMenuItemUseCase listUseCase;
    private final MenuItemWebMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItemResponsePayload create(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String restaurantId,
            @PathVariable String menuId,
            @RequestBody @Valid MenuItemRequestPayload payload
    ) {
        return mapper.toResponse(
                createUseCase.execute(
                        userId,
                        restaurantId,
                        menuId,
                        mapper.toCreateDto(payload)
                )
        );
    }

    @PutMapping("/{menuItemId}")
    public MenuItemResponsePayload update(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String restaurantId,
            @PathVariable String menuId,
            @PathVariable String menuItemId,
            @RequestBody @Valid MenuItemRequestPayload payload
    ) {
        return mapper.toResponse(
                updateUseCase.execute(
                        userId,
                        restaurantId,
                        menuId,
                        menuItemId,
                        mapper.toCreateDto(payload)
                )
        );
    }

    @DeleteMapping("/{menuItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String restaurantId,
            @PathVariable String menuId,
            @PathVariable String menuItemId
    ) {
        deleteUseCase.execute(
                userId,
                restaurantId,
                menuId,
                menuItemId
        );
    }

    @GetMapping("/{menuItemId}")
    public MenuItemResponsePayload getById(
            @PathVariable String restaurantId,
            @PathVariable String menuId,
            @PathVariable String menuItemId
    ) {
        return mapper.toResponse(
                listUseCase.execute(
                        restaurantId,
                        menuId,
                        menuItemId
                )
        );
    }
}
