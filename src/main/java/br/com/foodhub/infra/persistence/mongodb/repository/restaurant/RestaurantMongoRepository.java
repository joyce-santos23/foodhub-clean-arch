package br.com.foodhub.infra.persistence.mongodb.repository.restaurant;

import br.com.foodhub.infra.persistence.mongodb.document.restaurant.RestaurantDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantMongoRepository extends MongoRepository<RestaurantDocument, String> {
    boolean existsByCnpj(String cnpj);
}
