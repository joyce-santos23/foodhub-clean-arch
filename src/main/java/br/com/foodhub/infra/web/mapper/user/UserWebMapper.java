package br.com.foodhub.infra.web.mapper.user;

import br.com.foodhub.core.application.dto.user.UpdateUserDTO;
import br.com.foodhub.core.application.dto.user.UserRequestDTO;
import br.com.foodhub.core.application.dto.user.UserResultDTO;
import br.com.foodhub.infra.web.payload.user.UpdateUserPayload;
import br.com.foodhub.infra.web.payload.user.UserRequestPayload;
import br.com.foodhub.infra.web.payload.user.UserResponsePayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserWebMapper {

    UserRequestDTO toCreateDto(UserRequestPayload payload);

    UpdateUserDTO toUpdateDto(UpdateUserPayload payload);

    UserResponsePayload toResponse(UserResultDTO dto);
}
