package br.com.foodhub.infra.persistence.mongodb.document.menu;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDocument {

    private String id;
    private String name;
    private String description;
    private Double price;
    private boolean inRestaurantOnly;
    private String photograph;
}

