package br.com.foodhub.infra.web.controller.user;

import br.com.foodhub.core.application.dto.address.UpdateUserAddressDTO;
import br.com.foodhub.core.application.dto.address.UserAddressDTO;
import br.com.foodhub.core.application.dto.address.UserAddressResultDTO;
import br.com.foodhub.core.application.usecase.address.CreateUserAddressUseCase;
import br.com.foodhub.core.application.usecase.address.DeleteUserAddressUseCase;
import br.com.foodhub.core.application.usecase.address.ListUserAddressUseCase;
import br.com.foodhub.core.application.usecase.address.UpdateUserAddressUseCase;
import br.com.foodhub.infra.web.mapper.user.UserAddressMapper;
import br.com.foodhub.infra.web.payload.address.UserAddressResponsePayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserAddressController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserAddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserAddressUseCase createUserAddressUseCase;

    @MockBean
    private UpdateUserAddressUseCase updateUserAddressUseCase;

    @MockBean
    private ListUserAddressUseCase listUserAddressUseCase;

    @MockBean
    private DeleteUserAddressUseCase deleteUserAddressUseCase;

    @MockBean
    private UserAddressMapper mapper;


    @Test
    void shouldListUserAddresses() throws Exception {
        when(listUserAddressUseCase.execute(anyString()))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/users/{userId}/addresses", "123"))
                .andExpect(status().isOk());
    }


    @Test
    void shouldCreateUserAddress() throws Exception {
        when(mapper.toCreateDto(any()))
                .thenReturn(mock(UserAddressDTO.class));

        when(createUserAddressUseCase.execute(anyString(), any()))
                .thenReturn(mock(UserAddressResultDTO.class));

        when(mapper.toResponse(any()))
                .thenReturn(mock(UserAddressResponsePayload.class));

        mockMvc.perform(post("/api/v1/users/{userId}/addresses", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "cep": "01001000",
                  "number": "123",
                  "complement": "Apto 10",
                  "primary": true
                }
            """))
                .andExpect(status().isOk());
    }


    @Test
    void shouldReturn422WhenCreatePayloadInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/users/{userId}/addresses", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void shouldUpdateUserAddress() throws Exception {
        when(mapper.toUpdateDto(any()))
                .thenReturn(mock(UpdateUserAddressDTO.class));

        when(updateUserAddressUseCase.execute(anyString(), anyString(), any()))
                .thenReturn(mock(UserAddressResultDTO.class));

        when(mapper.toResponse(any()))
                .thenReturn(mock(UserAddressResponsePayload.class));

        mockMvc.perform(put("/api/v1/users/{userId}/addresses/{addressId}", "123", "456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "number": "456",
                      "complement": "Casa",
                      "primary": false
                    }
                """))
                .andExpect(status().isOk());
    }


    @Test
    void shouldDeleteUserAddress() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{userId}/addresses/{addressId}", "123", "456"))
                .andExpect(status().isOk());
    }
}
