package br.com.foodhub.core.application.usecase.userrestaurant;

import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.association.UserRestaurant;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnlinkUserFromRestaurantUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private UnlinkUserFromRestaurantUseCase useCase;

    private static final String USER_ID = "user-1";
    private static final String RESTAURANT_ID = "restaurant-1";
    private static final String USER_TYPE_ID = "type-1";

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userGateway.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(USER_ID, RESTAURANT_ID)
        );
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        User user = buildUser();

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(user));
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(USER_ID, RESTAURANT_ID)
        );
    }

    @Test
    void shouldThrowWhenUserIsRestaurantOwner() {
        User user = buildUser();

        Restaurant restaurant = Restaurant.reconstitute(
                RESTAURANT_ID,
                "Restaurante Teste",
                "qualquer",
                "Brasileira",
                USER_ID,
                "address-base-1",
                "100",
                null,
                List.of(),
                List.of()
        );

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(user));
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));

        assertThrows(
                BusinessRuleViolationException.class,
                () -> useCase.execute(USER_ID, RESTAURANT_ID)
        );
    }

    @Test
    void shouldThrowWhenUserIsNotLinkedToRestaurant() {
        User user = buildUser();

        Restaurant restaurant = Restaurant.reconstitute(
                RESTAURANT_ID,
                "Restaurante Teste",
                "qualquer",
                "Brasileira",
                "owner-1",
                "address-base-1",
                "100",
                null,
                List.of(),
                List.of()
        );

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(user));
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));

        assertThrows(
                BusinessRuleViolationException.class,
                () -> useCase.execute(USER_ID, RESTAURANT_ID)
        );
    }

    @Test
    void shouldUnlinkUserFromRestaurantSuccessfully() {
        User user = buildUser();

        user.addRestaurant(
                new UserRestaurant(
                        USER_ID,
                        RESTAURANT_ID,
                        USER_TYPE_ID
                )
        );

        Restaurant restaurant = Restaurant.reconstitute(
                RESTAURANT_ID,
                "Restaurante Teste",
                "qualquer",
                "Brasileira",
                "owner-1",
                "address-base-1",
                "100",
                null,
                List.of(),
                List.of()
        );

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(user));
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        when(userGateway.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        useCase.execute(USER_ID, RESTAURANT_ID);

        verify(userGateway).save(user);
    }

    private User buildUser() {
        UserType userType = mock(UserType.class);

        return User.reconstitute(
                USER_ID,
                "João",
                "joao@foodhub.com",
                "11999999999",
                "52998224725",
                "hashed-password",
                userType
        );
    }
}
