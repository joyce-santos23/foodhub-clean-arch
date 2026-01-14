package br.com.foodhub.infra.persistence.mongodb.document.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "address_base")
public class AddressBaseDocument {

    @Id
    private String id;

    private String cep;
    private String street;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
}

