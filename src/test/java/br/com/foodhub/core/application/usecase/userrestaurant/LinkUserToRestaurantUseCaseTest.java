package br.com.foodhub.core.application.usecase.userrestaurant;

import br.com.foodhub.core.application.dto.userrestaurant.LinkUserToRestaurantDTO;
import br.com.foodhub.core.application.dto.userrestaurant.UserRestaurantResultDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.application.port.user.UserTypeGateway;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkUserToRestaurantUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private LinkUserToRestaurantUseCase useCase;

    private static final String USER_ID = "user-1";
    private static final String RESTAURANT_ID = "restaurant-1";
    private static final String USER_TYPE_ID = "type-1";


    @Test
    void shouldLinkUserToRestaurantSuccessfully() {
        User user = buildUser();
        Restaurant restaurant = buildRestaurant();

        UserType userType = mock(UserType.class);
        when(userType.isRestaurantRelated()).thenReturn(true);
        when(userType.getId()).thenReturn(USER_TYPE_ID);

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(user));
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        when(userTypeGateway.findById(USER_TYPE_ID)).thenReturn(Optional.of(userType));
        when(userGateway.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LinkUserToRestaurantDTO dto =
                new LinkUserToRestaurantDTO(RESTAURANT_ID, USER_TYPE_ID);

        UserRestaurantResultDTO result =
                useCase.execute(USER_ID, dto);

        assertNotNull(result);
        assertEquals(USER_ID, result.userId());
        assertEquals(RESTAURANT_ID, result.restaurantId());
        assertEquals(USER_TYPE_ID, result.userTypeId());

        verify(userGateway).save(any(User.class));
    }


    @Test
    void shouldThrowWhenUserNotFound() {
        when(userGateway.findById(USER_ID)).thenReturn(Optional.empty());

        LinkUserToRestaurantDTO dto =
                new LinkUserToRestaurantDTO(RESTAURANT_ID, USER_TYPE_ID);

        assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(USER_ID, dto)
        );
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        User user = buildUser();

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(user));
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.empty());

        LinkUserToRestaurantDTO dto =
                new LinkUserToRestaurantDTO(RESTAURANT_ID, USER_TYPE_ID);

        assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(USER_ID, dto)
        );
    }

    @Test
    void shouldThrowWhenUserTypeNotFound() {
        User user = buildUser();
        Restaurant restaurant = buildRestaurant();

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(user));
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        when(userTypeGateway.findById(USER_TYPE_ID)).thenReturn(Optional.empty());

        LinkUserToRestaurantDTO dto =
                new LinkUserToRestaurantDTO(RESTAURANT_ID, USER_TYPE_ID);

        assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(USER_ID, dto)
        );
    }

    @Test
    void shouldThrowWhenUserTypeIsNotRestaurantRelated() {
        User user = buildUser();
        Restaurant restaurant = buildRestaurant();

        UserType userType = mock(UserType.class);
        when(userType.isRestaurantRelated()).thenReturn(false);

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(user));
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        when(userTypeGateway.findById(USER_TYPE_ID)).thenReturn(Optional.of(userType));

        LinkUserToRestaurantDTO dto =
                new LinkUserToRestaurantDTO(RESTAURANT_ID, USER_TYPE_ID);

        assertThrows(
                BusinessRuleViolationException.class,
                () -> useCase.execute(USER_ID, dto)
        );
    }


    @Test
    void shouldThrowWhenUserAlreadyLinkedToRestaurant() {
        User user = buildUser();
        Restaurant restaurant = buildRestaurant();
        UserType userType = mock(UserType.class);

        user.addRestaurant(
                new UserRestaurant(
                        USER_ID,
                        RESTAURANT_ID,
                        USER_TYPE_ID
                )
        );

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(user));
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        when(userTypeGateway.findById(USER_TYPE_ID)).thenReturn(Optional.of(userType));

        LinkUserToRestaurantDTO dto =
                new LinkUserToRestaurantDTO(RESTAURANT_ID, USER_TYPE_ID);

        assertThrows(
                BusinessRuleViolationException.class,
                () -> useCase.execute(USER_ID, dto)
        );
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



    private Restaurant buildRestaurant() {
        return Restaurant.reconstitute(
                RESTAURANT_ID,
                "Restaurante Teste",
                "qualquer-coisa-aqui",
                "Brasileira",
                "owner-1",
                "address-base-1",
                "100",
                null,
                List.of(),
                List.of()
        );
    }

}
