package br.com.foodhub.infra.persistence.mongodb.mapper.usertype;

import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.infra.persistence.mongodb.document.user.UserTypeDocument;
import org.springframework.stereotype.Component;

@Component
public class UserTypeMapper {

    public UserType toDomain(UserTypeDocument document) {
        if (document == null) return null;

        return UserType.reconstitute(
                document.getId(),
                document.getName()
        );
    }

    public UserTypeDocument toDocument(UserType domain) {
        if (domain == null) return null;

        UserTypeDocument doc = new UserTypeDocument();
        doc.setId(domain.getId());
        doc.setName(domain.getName());
        doc.setRestaurantRelated(domain.isRestaurantRelated());

        return doc;
    }
}

