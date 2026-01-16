package br.com.foodhub.infra.persistence.mongodb.document.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserDocument {

    @Id
    private String id;

    private String name;
    private String email;
    private String phone;
    private String cpf;
    private String password;
    private String userTypeId;

    // embedded
    private List<UserAddressDocument> addresses;
    private List<UserRestaurantDocument> restaurants;
}

