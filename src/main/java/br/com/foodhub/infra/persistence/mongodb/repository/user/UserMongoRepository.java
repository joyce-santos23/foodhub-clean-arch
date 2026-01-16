package br.com.foodhub.infra.persistence.mongodb.repository.user;

import br.com.foodhub.infra.persistence.mongodb.document.user.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<UserDocument, String> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByCpf(String cpf);

    boolean existsByUserTypeId(String userTypeId);
}
