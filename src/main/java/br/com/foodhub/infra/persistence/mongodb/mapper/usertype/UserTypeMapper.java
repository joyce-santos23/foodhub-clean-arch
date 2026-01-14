package br.com.foodhub.infra.persistence.mongodb.mapper.usertype;

import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.infra.persistence.mongodb.document.user.UserTypeDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {

    UserType toDomain(UserTypeDocument document);

    UserTypeDocument toDocument(UserType domain);
}
