package br.com.foodhub.infra.web.controller.user.usertype;

import br.com.foodhub.core.application.dto.user.usertype.UserTypeRequestDTO;
import br.com.foodhub.core.application.dto.user.usertype.UserTypeResultDTO;
import br.com.foodhub.core.application.usecase.user.usertype.CreateUserTypeUseCase;
import br.com.foodhub.core.application.usecase.user.usertype.DeleteUserTypeUseCase;
import br.com.foodhub.core.application.usecase.user.usertype.ListUserTypeUseCase;
import br.com.foodhub.core.application.usecase.user.usertype.UpdateUserTypeUseCase;
import br.com.foodhub.infra.web.mapper.usertype.UserTypeWebMapper;
import br.com.foodhub.infra.web.payload.user.usertype.UserTypeResponsePayload;
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

@WebMvcTest(UserTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserTypeUseCase createUserTypeUseCase;

    @MockBean
    private UpdateUserTypeUseCase updateUserTypeUseCase;

    @MockBean
    private DeleteUserTypeUseCase deleteUserTypeUseCase;

    @MockBean
    private ListUserTypeUseCase listUserTypeUseCase;

    @MockBean
    private UserTypeWebMapper mapper;


    @Test
    void shouldCreateUserType() throws Exception {
        when(mapper.toCreateDto(any()))
                .thenReturn(mock(UserTypeRequestDTO.class));

        when(createUserTypeUseCase.execute(any()))
                .thenReturn(mock(UserTypeResultDTO.class));

        when(mapper.toResponse(any()))
                .thenReturn(mock(UserTypeResponsePayload.class));

        mockMvc.perform(post("/api/v1/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "name": "ADMIN"
                    }
                """))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn422WhenCreatePayloadInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldUpdateUserType() throws Exception {
        when(mapper.toCreateDto(any()))
                .thenReturn(mock(UserTypeRequestDTO.class));

        when(updateUserTypeUseCase.execute(anyString(), any()))
                .thenReturn(mock(UserTypeResultDTO.class));

        when(mapper.toResponse(any()))
                .thenReturn(mock(UserTypeResponsePayload.class));

        mockMvc.perform(put("/api/v1/user-types/{id}", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "name": "MANAGER"
                    }
                """))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn422WhenUpdatePayloadInvalid() throws Exception {
        mockMvc.perform(put("/api/v1/user-types/{id}", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void shouldListUserTypes() throws Exception {
        when(listUserTypeUseCase.execute())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/user-types"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteUserType() throws Exception {
        mockMvc.perform(delete("/api/v1/user-types/{id}", "123"))
                .andExpect(status().isNoContent());
    }
}
