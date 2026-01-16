package br.com.foodhub.infra.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI foodHubOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FoodHub API")
                        .description("API responsável pela gestão de usuários, restaurantes e cardápios ")
                        .version("v1")
                        .contact(new Contact()
                                .name("FoodHub Team")
                                .email("contato@foodhub.com")
                        )
                );
    }
}
