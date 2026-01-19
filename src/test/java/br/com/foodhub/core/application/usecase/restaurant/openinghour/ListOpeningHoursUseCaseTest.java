package br.com.foodhub.core.application.usecase.restaurant.openinghour;

import br.com.foodhub.core.application.dto.restaurant.openingHour.OpeningHoursResultDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.restaurant.OpeningHours;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListOpeningHoursUseCaseTest {

    @Mock
    RestaurantGateway restaurantGateway;

    @InjectMocks
    ListOpeningHoursUseCase useCase;

    @Test
    void shouldListOpeningHoursSuccessfully() {
        Restaurant restaurant = restaurantWithOpeningHours();

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        List<OpeningHoursResultDTO> result =
                useCase.execute("r1");

        assertEquals(2, result.size());

        OpeningHoursResultDTO monday = result.get(0);
        assertEquals(DayOfWeek.MONDAY, monday.dayOfWeek());
        assertEquals(LocalTime.of(9, 0), monday.openingTime());
        assertEquals(LocalTime.of(18, 0), monday.closingTime());
        assertFalse(monday.closed());

        OpeningHoursResultDTO sunday = result.get(1);
        assertEquals(DayOfWeek.SUNDAY, sunday.dayOfWeek());
        assertTrue(sunday.closed());
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("r1"));

        verify(restaurantGateway).findById("r1");
        verifyNoMoreInteractions(restaurantGateway);
    }


    /* =========================
       HELPERS
       ========================= */

    private Restaurant restaurantWithOpeningHours() {
        Restaurant restaurant = Restaurant.reconstitute(
                "r1",
                "Restaurante Teste",
                "12345678000190",
                "Italiana",
                "u1",
                "addr1",
                "100",
                null,
                List.of(
                        new OpeningHours(
                                DayOfWeek.MONDAY,
                                LocalTime.of(9, 0),
                                LocalTime.of(18, 0),
                                false
                        ),
                        new OpeningHours(
                                DayOfWeek.SUNDAY,
                                null,
                                null,
                                true
                        )
                ),
                null
        );
        return restaurant;
    }
}
