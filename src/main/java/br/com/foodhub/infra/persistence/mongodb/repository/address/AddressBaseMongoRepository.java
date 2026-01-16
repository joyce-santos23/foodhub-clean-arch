package br.com.foodhub.infra.persistence.mongodb.repository.address;

import br.com.foodhub.core.domain.entity.address.AddressBase;
import br.com.foodhub.infra.persistence.mongodb.document.address.AddressBaseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AddressBaseMongoRepository extends MongoRepository<AddressBaseDocument, String> {
    Optional<AddressBaseDocument> findByCep(String cep);
}
