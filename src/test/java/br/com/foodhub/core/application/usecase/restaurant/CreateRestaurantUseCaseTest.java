package br.com.foodhub.core.application.usecase.restaurant;

import br.com.foodhub.core.application.dto.restaurant.RestaurantRequestDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.application.usecase.address.FindOrCreateAddressBaseUseCase;
import br.com.foodhub.core.domain.entity.address.AddressBase;
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
class CreateRestaurantUseCaseTest {

    @Mock
    RestaurantGateway restaurantGateway;

    @Mock
    UserGateway userGateway;

    @Mock
    FindOrCreateAddressBaseUseCase addressUseCase;

    @InjectMocks
    CreateRestaurantUseCase useCase;


    @Test
    void shouldCreateRestaurantSuccessfully() {
        User owner = ownerUser("owner1");
        AddressBase address = address("addr1");

        when(userGateway.findById("owner1"))
                .thenReturn(Optional.of(owner));

        when(restaurantGateway.existsByCnpj("12345678000190"))
                .thenReturn(false);

        when(addressUseCase.execute("81880390"))
                .thenReturn(address);

        when(restaurantGateway.save(any(Restaurant.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        var result = useCase.execute("owner1", validRequest());

        assertEquals("Pizzaria Napoli", result.businessName());
        verify(restaurantGateway).save(any(Restaurant.class));
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userGateway.findById("owner1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("owner1", validRequest()));

        verify(restaurantGateway, never()).save(any());
    }

    @Test
    void shouldThrowWhenUserCannotCreateRestaurant() {
        User customer = customerUser("u1");

        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(customer));

        assertThrows(BusinessRuleViolationException.class,
                () -> useCase.execute("u1", validRequest()));

        verify(restaurantGateway, never()).save(any());
    }

    @Test
    void shouldThrowWhenCnpjAlreadyExists() {
        User owner = ownerUser("owner1");

        when(userGateway.findById("owner1"))
                .thenReturn(Optional.of(owner));

        when(restaurantGateway.existsByCnpj("12345678000190"))
                .thenReturn(true);

        assertThrows(BusinessRuleViolationException.class,
                () -> useCase.execute("owner1", validRequest()));

        verify(restaurantGateway, never()).save(any());
    }

    /* =========================
       HELPERS
       ========================= */

    private RestaurantRequestDTO validRequest() {
        return new RestaurantRequestDTO(
                "Pizzaria Napoli",
                "12.345.678/0001-90",
                "Italiana",
                "81880390",
                "450",
                "Loja 2"
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

    private AddressBase address(String id) {
        return AddressBase.reconstitute(
                id,
                "81880390",
                "Rua Carlópolis",
                "Pinheirinho",
                "Curitiba",
                "PR",
                "Brasil"
        );
    }
}
