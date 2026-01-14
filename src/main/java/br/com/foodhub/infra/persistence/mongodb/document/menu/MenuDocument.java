package br.com.foodhub.infra.persistence.mongodb.document.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDocument {

    private String id;
    private String name;
    private List<MenuItemDocument> items;
}

