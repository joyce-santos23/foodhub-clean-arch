package br.com.foodhub.infra.persistence.mongodb.repository.usertype;

import br.com.foodhub.infra.persistence.mongodb.document.user.UserTypeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserTypeMongoRepository extends MongoRepository<UserTypeDocument, String> {
}
