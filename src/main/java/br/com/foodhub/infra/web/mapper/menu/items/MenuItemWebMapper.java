package br.com.foodhub.infra.web.mapper.menu.items;

import br.com.foodhub.core.application.dto.menu.items.MenuItemRequestDTO;
import br.com.foodhub.core.application.dto.menu.items.MenuItemResultDTO;
import br.com.foodhub.infra.web.payload.menu.items.MenuItemRequestPayload;
import br.com.foodhub.infra.web.payload.menu.items.MenuItemResponsePayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuItemWebMapper {

    MenuItemRequestDTO toCreateDto(MenuItemRequestPayload payload);

    MenuItemResponsePayload toResponse(MenuItemResultDTO dto);
}
