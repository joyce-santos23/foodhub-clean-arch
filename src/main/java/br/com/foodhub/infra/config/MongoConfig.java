package br.com.foodhub.infra.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class MongoConfig {

    @Value("${SPRING_DATA_MONGODB_URI}")
    private String mongoUri;

    @Bean
    public MongoClient mongoClient() {
        System.out.println("DEBUG: Conectando no Mongo usando URI: " + mongoUri);
        return MongoClients.create(mongoUri);
    }
}
