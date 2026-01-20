package br.com.foodhub.core.application.usecase.restaurant;

import br.com.foodhub.core.application.dto.restaurant.UpdateRestaurantDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRestaurantUseCaseTest {

    @Mock
    RestaurantGateway restaurantGateway;

    @Mock
    UserGateway userGateway;

    @InjectMocks
    UpdateRestaurantUseCase useCase;

    @Test
    void shouldUpdateRestaurantSuccessfully() {
        User owner = ownerUser("owner1");
        Restaurant restaurant = restaurant("r1", "owner1");

        when(userGateway.findById("owner1"))
                .thenReturn(Optional.of(owner));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        when(restaurantGateway.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        var dto = new UpdateRestaurantDTO(
                "Novo Nome",
                "Japonesa",
                "200",
                "Sala 3"
        );

        var result = useCase.execute("owner1", "r1", dto);

        assertEquals("Novo Nome", result.businessName());
        assertEquals("Japonesa", result.cuisineType());

        verify(restaurantGateway).save(restaurant);
    }


    @Test
    void shouldThrowWhenUserNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1", "r1", updateDto()));

        verify(restaurantGateway, never()).save(any());
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        User owner = ownerUser("owner1");

        when(userGateway.findById("owner1"))
                .thenReturn(Optional.of(owner));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("owner1", "r1", updateDto()));

        verify(restaurantGateway, never()).save(any());
    }

    @Test
    void shouldThrowWhenUserCannotManageRestaurant() {
        User customer = customerUser("u1");
        Restaurant restaurant = restaurant("r1", "owner1");

        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(customer));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        assertThrows(BusinessRuleViolationException.class,
                () -> useCase.execute("u1", "r1", updateDto()));

        verify(restaurantGateway, never()).save(any());
    }

    /* =========================
       HELPERS
       ========================= */

    private UpdateRestaurantDTO updateDto() {
        return new UpdateRestaurantDTO(
                "Novo Nome",
                "Japonesa",
                "200",
                "Sala 3"
        );
    }

    private User ownerUser(String id) {
        return User.reconstitute(
                id,
                "Owner",
                "owner@email.com",
                "11999999999",
                null,
                "pwd",
                UserType.reconstitute("t1", "OWNER")
        );
    }

    private User customerUser(String id) {
        return User.reconstitute(
                id,
                "Customer",
                "cust@email.com",
                "11999999999",
                null,
                "pwd",
                UserType.reconstitute("t2", "CUSTOMER")
        );
    }

    private Restaurant restaurant(String id, String ownerId) {
        return Restaurant.reconstitute(
                id,
                "Restaurante",
                "12345678000190",
                "Italiana",
                ownerId,
                "addr1",
                "100",
                null,
                null,
                null
        );
    }
}
