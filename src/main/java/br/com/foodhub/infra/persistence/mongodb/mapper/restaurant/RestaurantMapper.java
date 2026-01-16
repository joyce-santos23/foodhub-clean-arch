package br.com.foodhub.infra.persistence.mongodb.mapper.restaurant;

import br.com.foodhub.core.domain.entity.menu.Menu;
import br.com.foodhub.core.domain.entity.menu.MenuItem;
import br.com.foodhub.core.domain.entity.restaurant.OpeningHours;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.infra.persistence.mongodb.document.menu.MenuDocument;
import br.com.foodhub.infra.persistence.mongodb.document.menu.MenuItemDocument;
import br.com.foodhub.infra.persistence.mongodb.document.restaurant.OpeningHoursDocument;
import br.com.foodhub.infra.persistence.mongodb.document.restaurant.RestaurantDocument;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantMapper {

    public Restaurant toDomain(RestaurantDocument doc) {

        List<Menu> menus = doc.getMenus() == null
                ? List.of()
                : doc.getMenus().stream()
                .map(this::mapMenu)
                .toList();

        return Restaurant.reconstitute(
                doc.getId(),
                doc.getBusinessName(),
                doc.getCnpj(),
                doc.getCuisineType(),
                doc.getOwnerId(),
                doc.getAddressBaseId(),
                doc.getNumberStreet(),
                doc.getComplement(),
                mapOpeningHours(doc.getOpeningHours()),
                menus
        );
    }

    protected Menu mapMenu(MenuDocument md) {

        List<MenuItem> items = md.getItems() == null
                ? List.of()
                : md.getItems().stream()
                .map(this::mapMenuItem)
                .toList();

        return Menu.reconstitute(
                md.getId(),
                md.getName(),
                items
        );
    }

    protected MenuItem mapMenuItem(MenuItemDocument mi) {
        return MenuItem.reconstitute(
                mi.getId(),
                mi.getName(),
                mi.getDescription(),
                mi.getPrice(),
                mi.isInRestaurantOnly(),
                mi.getPhotograph()
        );
    }

    protected List<OpeningHours> mapOpeningHours(List<OpeningHoursDocument> docs) {
        if (docs == null) return List.of();

        return docs.stream()
                .map(d -> new OpeningHours(
                        d.getDayOfWeek(),
                        d.getOpenTime(),
                        d.getCloseTime(),
                        d.isClosed()
                ))
                .toList();
    }

    public RestaurantDocument toDocument(Restaurant restaurant) {
        if (restaurant == null) return null;

        RestaurantDocument doc = new RestaurantDocument();

        doc.setId(restaurant.getId());
        doc.setBusinessName(restaurant.getBusinessName());
        doc.setCnpj(restaurant.getCnpj());
        doc.setCuisineType(restaurant.getCuisineType());
        doc.setOwnerId(restaurant.getOwnerId());
        doc.setAddressBaseId(restaurant.getAddressBaseId());
        doc.setNumberStreet(restaurant.getNumberStreet());
        doc.setComplement(restaurant.getComplement());

        // opening hours
        if (restaurant.getOpeningHours() != null) {
            doc.setOpeningHours(
                    restaurant.getOpeningHours().stream()
                            .map(oh -> new OpeningHoursDocument(
                                    oh.getDayOfWeek(),
                                    oh.getOpenTime(),
                                    oh.getCloseTime(),
                                    oh.isClosed()
                            ))
                            .toList()
            );
        }

        // menus
        if (restaurant.getMenus() != null) {
            doc.setMenus(
                    restaurant.getMenus().stream()
                            .map(menu -> {
                                MenuDocument md = new MenuDocument();
                                md.setId(menu.getId());
                                md.setName(menu.getName());

                                if (menu.getItems() != null) {
                                    md.setItems(
                                            menu.getItems().stream()
                                                    .map(item -> new MenuItemDocument(
                                                            item.getId(),
                                                            item.getName(),
                                                            item.getDescription(),
                                                            item.getPrice(),
                                                            item.isInRestaurantOnly(),
                                                            item.getPhotograph()
                                                    ))
                                                    .toList()
                                    );
                                }

                                return md;
                            })
                            .toList()
            );
        }

        return doc;
    }

}



