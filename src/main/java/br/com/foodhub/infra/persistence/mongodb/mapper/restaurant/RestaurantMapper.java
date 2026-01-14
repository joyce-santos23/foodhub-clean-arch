package br.com.foodhub.infra.persistence.mongodb.mapper.restaurant;

import br.com.foodhub.core.domain.entity.menu.Menu;
import br.com.foodhub.core.domain.entity.menu.MenuItem;
import br.com.foodhub.core.domain.entity.restaurant.OpeningHours;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.infra.persistence.mongodb.document.menu.MenuDocument;
import br.com.foodhub.infra.persistence.mongodb.document.menu.MenuItemDocument;
import br.com.foodhub.infra.persistence.mongodb.document.restaurant.OpeningHoursDocument;
import br.com.foodhub.infra.persistence.mongodb.document.restaurant.RestaurantDocument;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RestaurantMapper {

    public Restaurant toDomain(RestaurantDocument doc) {

        Restaurant restaurant = new Restaurant(
                doc.getBusinessName(),
                doc.getCnpj(),
                doc.getCuisineType(),
                doc.getOwnerId(),
                doc.getAddressBaseId(),
                doc.getNumberStreet(),
                doc.getComplement(),
                mapOpeningHours(doc.getOpeningHours())
        );
        setId(restaurant, doc.getId());

        if (doc.getMenus() != null) {
            for (MenuDocument md : doc.getMenus()) {
                Menu menu = mapMenu(md);
                restaurant.addMenu(menu);
            }
        }

        return restaurant;
    }

    public abstract RestaurantDocument toDocument(Restaurant restaurant);

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

    protected Menu mapMenu(MenuDocument md) {
        Menu menu = new Menu(md.getName());
        setId(menu, md.getId());

        if (md.getItems() != null) {
            for (MenuItemDocument mi : md.getItems()) {
                MenuItem item = new MenuItem(
                        mi.getName(),
                        mi.getDescription(),
                        mi.getPrice(),
                        mi.isInRestaurantOnly(),
                        mi.getPhotograph()
                );
                setId(item, mi.getId());
                menu.addItem(item);
            }
        }
        return menu;
    }

    protected void setId(Object entity, String id) {
        try {
            var field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set id on entity", e);
        }
    }
}
