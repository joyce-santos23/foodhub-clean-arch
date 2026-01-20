package br.com.foodhub.infra.web.controller.restaurant.openinghour;

import br.com.foodhub.core.application.dto.restaurant.openingHour.OpeningHoursResultDTO;
import br.com.foodhub.core.application.dto.restaurant.openingHour.UpdateOpeningHoursDTO;
import br.com.foodhub.core.application.usecase.restaurant.openinghour.ChangeOpeningHoursUseCase;
import br.com.foodhub.core.application.usecase.restaurant.openinghour.ListOpeningHoursUseCase;
import br.com.foodhub.infra.web.mapper.restaurant.openinghour.OpeningHoursWebMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OpeningHoursController.class)
@AutoConfigureMockMvc(addFilters = false)
class OpeningHoursControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChangeOpeningHoursUseCase changeUseCase;

    @MockBean
    private ListOpeningHoursUseCase listUseCase;

    @MockBean
    private OpeningHoursWebMapper mapper;

    @Test
    void shouldListOpeningHours() throws Exception {
        when(listUseCase.execute(anyString()))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/restaurants/{restaurantId}/opening-hours", "123"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldChangeOpeningHours() throws Exception {
        when(mapper.toDto(any()))
                .thenReturn(mock(UpdateOpeningHoursDTO.class));

        when(listUseCase.execute(anyString()))
                .thenReturn(List.of(mock(OpeningHoursResultDTO.class)));

        mockMvc.perform(put("/api/v1/restaurants/{restaurantId}/opening-hours/{dayOfWeek}",
                        "123", DayOfWeek.MONDAY)
                        .header("X-User-Id", "user-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "openTime": "09:00",
                              "closeTime": "18:00",
                              "closed": false
                            }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn400WhenDayOfWeekInvalid() throws Exception {
        mockMvc.perform(put("/api/v1/restaurants/{restaurantId}/opening-hours/{dayOfWeek}",
                        "123", "SEGUNDA")
                        .header("X-User-Id", "user-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "openTime": "09:00",
                              "closeTime": "18:00",
                              "closed": false
                            }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenHeaderMissing() throws Exception {
        mockMvc.perform(put("/api/v1/restaurants/{restaurantId}/opening-hours/{dayOfWeek}",
                        "123", DayOfWeek.MONDAY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "openTime": "09:00",
                              "closeTime": "18:00",
                              "closed": false
                            }
                        """))
                .andExpect(status().isBadRequest());
    }
}
