package br.com.foodhub.infra.web.controller.user;

import br.com.foodhub.core.application.dto.userrestaurant.LinkUserToRestaurantDTO;
import br.com.foodhub.core.application.dto.userrestaurant.UserRestaurantResultDTO;
import br.com.foodhub.core.application.usecase.userrestaurant.LinkUserToRestaurantUseCase;
import br.com.foodhub.core.application.usecase.userrestaurant.UnlinkUserFromRestaurantUseCase;
import br.com.foodhub.infra.web.mapper.userrestaurant.UserRestaurantWebMapper;
import br.com.foodhub.infra.web.payload.userrestaurant.UserRestaurantResponsePayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserRestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LinkUserToRestaurantUseCase linkUserToRestaurantUseCase;

    @MockBean
    private UnlinkUserFromRestaurantUseCase unlinkUserFromRestaurantUseCase;

    @MockBean
    private UserRestaurantWebMapper mapper;


    @Test
    void shouldLinkUserToRestaurant() throws Exception {
        when(mapper.toDto(any()))
                .thenReturn(mock(LinkUserToRestaurantDTO.class));

        when(linkUserToRestaurantUseCase.execute(anyString(), any()))
                .thenReturn(mock(UserRestaurantResultDTO.class));

        when(mapper.toResponse(any()))
                .thenReturn(mock(UserRestaurantResponsePayload.class));

        mockMvc.perform(post("/api/v1/users/{userId}/restaurants", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "restaurantId": "rest-1",
                      "userTypeId": "ADMIN"
                    }
                """))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn422WhenLinkPayloadInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/users/{userId}/restaurants", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void shouldUnlinkUserFromRestaurant() throws Exception {
        mockMvc.perform(delete(
                        "/api/v1/users/{userId}/restaurants/{restaurantId}",
                        "123",
                        "456"
                ))
                .andExpect(status().isNoContent());
    }
}
