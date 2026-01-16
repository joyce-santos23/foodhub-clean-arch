package br.com.foodhub.infra.persistence.mongodb.document.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDocument {

    private String id;
    private String addressBaseId;
    private String number;
    private String complement;
    private boolean primary;
}

