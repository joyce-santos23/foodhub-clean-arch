package br.com.foodhub.infra.web.controller.restaurant;

import br.com.foodhub.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.core.application.dto.restaurant.RestaurantRequestDTO;
import br.com.foodhub.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.core.application.dto.restaurant.UpdateRestaurantDTO;
import br.com.foodhub.core.application.port.address.AddressBaseGateway;
import br.com.foodhub.core.application.usecase.restaurant.CreateRestaurantUseCase;
import br.com.foodhub.core.application.usecase.restaurant.DeleteRestaurantUseCase;
import br.com.foodhub.core.application.usecase.restaurant.ListAllRestaurantUseCase;
import br.com.foodhub.core.application.usecase.restaurant.ListRestaurantByIdUseCase;
import br.com.foodhub.core.application.usecase.restaurant.UpdateRestaurantUseCase;
import br.com.foodhub.core.domain.entity.address.AddressBase;
import br.com.foodhub.infra.web.mapper.restaurant.RestaurantWebMapper;
import br.com.foodhub.infra.web.payload.restaurant.RestaurantResponsePayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateRestaurantUseCase createRestaurantUseCase;

    @MockBean
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @MockBean
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    @MockBean
    private ListAllRestaurantUseCase listAllRestaurantUseCase;

    @MockBean
    private ListRestaurantByIdUseCase listRestaurantByIdUseCase;

    @MockBean
    private AddressBaseGateway addressGateway;

    @MockBean
    private RestaurantWebMapper mapper;

    @Test
    void shouldCreateRestaurant() throws Exception {
        when(mapper.toCreateDto(any()))
                .thenReturn(mock(RestaurantRequestDTO.class));

        when(createRestaurantUseCase.execute(anyString(), any()))
                .thenReturn(mock(RestaurantResultDTO.class));

        when(addressGateway.findById(any()))
                .thenReturn(Optional.of(mock(AddressBase.class)));

        when(mapper.toResponse(any(), any()))
                .thenReturn(mock(RestaurantResponsePayload.class));

        mockMvc.perform(post("/api/v1/restaurants")
                        .header("X-User-Id", "user-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "businessName": "Restaurante X",
                                "cnpj": "12345678000199",
                                "cuisineType": "ITALIANA",
                                "cep": "01001000",
                                "numberStreet": "123"
                            }
                        """))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn422WhenCreatePayloadInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/restaurants")
                        .header("X-User-Id", "user-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldReturn400WhenHeaderMissingOnCreate() throws Exception {
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldUpdateRestaurant() throws Exception {
        when(mapper.toUpdateDto(any()))
                .thenReturn(mock(UpdateRestaurantDTO.class));

        when(updateRestaurantUseCase.execute(anyString(), anyString(), any()))
                .thenReturn(mock(RestaurantResultDTO.class));

        when(addressGateway.findById(any()))
                .thenReturn(Optional.of(mock(AddressBase.class)));

        when(mapper.toResponse(any(), any()))
                .thenReturn(mock(RestaurantResponsePayload.class));

        mockMvc.perform(put("/api/v1/restaurants/{id}", "123")
                        .header("X-User-Id", "user-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "Novo Nome"
                            }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteRestaurant() throws Exception {
        mockMvc.perform(delete("/api/v1/restaurants/{id}", "123")
                        .header("X-User-Id", "user-1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetRestaurantById() throws Exception {
        when(listRestaurantByIdUseCase.execute(anyString()))
                .thenReturn(mock(RestaurantResultDTO.class));

        when(addressGateway.findById(any()))
                .thenReturn(Optional.of(mock(AddressBase.class)));

        when(mapper.toResponse(any(), any()))
                .thenReturn(mock(RestaurantResponsePayload.class));

        mockMvc.perform(get("/api/v1/restaurants/{id}", "123"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldListRestaurants() throws Exception {
        when(listAllRestaurantUseCase.execute(any()))
                .thenReturn(new PageResultDTO<>(
                        List.of(),
                        0,
                        10,
                        0,
                        0
                ));

        mockMvc.perform(get("/api/v1/restaurants"))
                .andExpect(status().isOk());
    }
}
