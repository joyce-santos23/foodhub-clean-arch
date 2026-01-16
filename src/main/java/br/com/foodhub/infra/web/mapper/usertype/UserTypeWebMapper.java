package br.com.foodhub.infra.web.mapper.usertype;

import br.com.foodhub.core.application.dto.user.usertype.UserTypeRequestDTO;
import br.com.foodhub.core.application.dto.user.usertype.UserTypeResultDTO;
import br.com.foodhub.infra.web.payload.user.usertype.UserTypeRequestPayload;
import br.com.foodhub.infra.web.payload.user.usertype.UserTypeResponsePayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTypeWebMapper {

    UserTypeRequestDTO toCreateDto(UserTypeRequestPayload payload);

    UserTypeResponsePayload toResponse(UserTypeResultDTO dto);
}
