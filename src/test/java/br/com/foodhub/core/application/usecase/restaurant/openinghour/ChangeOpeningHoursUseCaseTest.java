package br.com.foodhub.core.application.usecase.restaurant.openinghour;

import br.com.foodhub.core.application.dto.restaurant.openingHour.UpdateOpeningHoursDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeOpeningHoursUseCaseTest {

    @Mock
    RestaurantGateway restaurantGateway;

    @Mock
    UserGateway userGateway;

    @InjectMocks
    ChangeOpeningHoursUseCase useCase;

    @Test
    void shouldChangeOpeningHoursSuccessfully() {
        User user = userOwner();
        Restaurant restaurant = restaurant("r1");

        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(user));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        UpdateOpeningHoursDTO dto = new UpdateOpeningHoursDTO(
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                false
        );

        useCase.execute("u1", "r1", DayOfWeek.MONDAY, dto);

        verify(restaurantGateway).save(restaurant);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.empty());

        UpdateOpeningHoursDTO dto = new UpdateOpeningHoursDTO(
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                false
        );

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1", "r1", DayOfWeek.MONDAY, dto));

        verifyNoInteractions(restaurantGateway);
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(userOwner()));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.empty());

        UpdateOpeningHoursDTO dto = new UpdateOpeningHoursDTO(
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                false
        );

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1", "r1", DayOfWeek.MONDAY, dto));

        verify(restaurantGateway, never()).save(any());
    }

    @Test
    void shouldThrowWhenUserCannotManageRestaurant() {
        User user = userWithoutPermission();
        Restaurant restaurant = restaurant("r1");

        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(user));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        UpdateOpeningHoursDTO dto = new UpdateOpeningHoursDTO(
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                false
        );

        assertThrows(RuntimeException.class,
                () -> useCase.execute("u1", "r1", DayOfWeek.MONDAY, dto));

        verify(restaurantGateway, never()).save(any());
    }

    /* =========================
       HELPERS
       ========================= */

    private User userOwner() {
        UserType ownerType = UserType.reconstitute(
                "type-owner",
                "OWNER"
        );

        return User.reconstitute(
                "u1",
                "John",
                "john@email.com",
                "11999999999",
                null,
                "hashed",
                ownerType
        );
    }

    private User userWithoutPermission() {
        UserType customerType = UserType.reconstitute(
                "type-customer",
                "CUSTOMER"
        );

        return User.reconstitute(
                "u1",
                "John",
                "john@email.com",
                "11999999999",
                null,
                "hashed",
                customerType
        );
    }

    private Restaurant restaurant(String id) {
        return Restaurant.reconstitute(
                id,
                "Restaurante Teste",
                "12345678000190",
                "Italiana",
                "u1",
                "addr1",
                "100",
                null,
                null,
                null
        );
    }
}
