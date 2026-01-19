package br.com.foodhub.core.domain.entity.restaurant;

import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import br.com.foodhub.core.domain.exceptions.restaurant.InvalidOpeningHoursException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class OpeningHoursTest {

    @Test
    void shouldCreateOpeningHoursWhenOpen() {
        OpeningHours hours = new OpeningHours(
                DayOfWeek.MONDAY,
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                false
        );

        assertEquals(DayOfWeek.MONDAY, hours.getDayOfWeek());
        assertEquals(LocalTime.of(9, 0), hours.getOpenTime());
        assertEquals(LocalTime.of(18, 0), hours.getCloseTime());
        assertFalse(hours.isClosed());
    }

    @Test
    void shouldThrowExceptionWhenOpenTimeIsAfterCloseTime() {
        assertThrows(InvalidOpeningHoursException.class,
                () -> new OpeningHours(
                        DayOfWeek.MONDAY,
                        LocalTime.of(18, 0),
                        LocalTime.of(9, 0),
                        false
                ));
    }

    @Test
    void shouldThrowExceptionWhenOpenTimeEqualsCloseTime() {
        assertThrows(InvalidOpeningHoursException.class,
                () -> new OpeningHours(
                        DayOfWeek.MONDAY,
                        LocalTime.of(9, 0),
                        LocalTime.of(9, 0),
                        false
                ));
    }

    /* =========================
       CONSTRUCTOR - CLOSED
       ========================= */

    @Test
    void shouldCreateClosedOpeningHours() {
        OpeningHours hours = new OpeningHours(
                DayOfWeek.SUNDAY,
                null,
                null,
                true
        );

        assertTrue(hours.isClosed());
        assertNull(hours.getOpenTime());
        assertNull(hours.getCloseTime());
    }

    @Test
    void shouldThrowExceptionIfClosedAndOpenTimeIsProvided() {
        assertThrows(InvalidOpeningHoursException.class,
                () -> new OpeningHours(
                        DayOfWeek.SUNDAY,
                        LocalTime.of(9, 0),
                        null,
                        true
                ));
    }


    @Test
    void shouldThrowExceptionWhenDayOfWeekIsNull() {
        assertThrows(RequiredFieldException.class,
                () -> new OpeningHours(
                        null,
                        LocalTime.of(9, 0),
                        LocalTime.of(18, 0),
                        false
                ));
    }


    @Test
    void shouldReturnTrueWhenTimeIsWithinOpeningHours() {
        OpeningHours hours = openFrom9To18();

        assertTrue(hours.isOpenAt(LocalTime.of(10, 0)));
    }

    @Test
    void shouldReturnFalseWhenTimeIsBeforeOpening() {
        OpeningHours hours = openFrom9To18();

        assertFalse(hours.isOpenAt(LocalTime.of(8, 59)));
    }

    @Test
    void shouldReturnFalseWhenTimeIsAfterClosing() {
        OpeningHours hours = openFrom9To18();

        assertFalse(hours.isOpenAt(LocalTime.of(18, 0)));
    }

    @Test
    void shouldReturnFalseWhenClosed() {
        OpeningHours hours = new OpeningHours(
                DayOfWeek.SUNDAY,
                null,
                null,
                true
        );

        assertFalse(hours.isOpenAt(LocalTime.of(12, 0)));
    }


    private OpeningHours openFrom9To18() {
        return new OpeningHours(
                DayOfWeek.MONDAY,
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                false
        );
    }
}
