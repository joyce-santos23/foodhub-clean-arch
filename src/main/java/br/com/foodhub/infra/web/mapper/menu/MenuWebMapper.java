package br.com.foodhub.infra.web.mapper.menu;

import br.com.foodhub.core.application.dto.menu.MenuRequestDTO;
import br.com.foodhub.core.application.dto.menu.MenuResultDTO;
import br.com.foodhub.core.application.dto.menu.MenuWithItemsResultDTO;
import br.com.foodhub.infra.web.payload.menu.CreateMenuRequestPayload;
import br.com.foodhub.infra.web.payload.menu.MenuResponsePayload;
import br.com.foodhub.infra.web.payload.menu.MenuWithItemsResponsePayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuWebMapper {

    MenuRequestDTO toCreateDto(CreateMenuRequestPayload payload);

    MenuResponsePayload toResponse(MenuResultDTO dto);

    MenuWithItemsResponsePayload toWithItemsResponse(MenuWithItemsResultDTO dto);
}
