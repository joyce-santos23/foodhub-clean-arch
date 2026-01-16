package br.com.foodhub.infra.persistence.mongodb.repository.usertype;

import br.com.foodhub.infra.persistence.mongodb.document.user.UserTypeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserTypeMongoRepository
        extends MongoRepository<UserTypeDocument, String> {

    Optional<UserTypeDocument> findByName(String name);
}
