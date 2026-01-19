package br.com.foodhub.infra.web.controller.user;

import br.com.foodhub.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.core.application.dto.user.UpdateUserDTO;
import br.com.foodhub.core.application.dto.user.UserRequestDTO;
import br.com.foodhub.core.application.dto.user.UserResultDTO;
import br.com.foodhub.core.application.usecase.user.CreateUserUseCase;
import br.com.foodhub.core.application.usecase.user.ListUserUseCase;
import br.com.foodhub.core.application.usecase.user.UpdateUserUseCase;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.infra.web.mapper.user.UserWebMapper;
import br.com.foodhub.infra.web.payload.user.UserResponsePayload;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @MockBean
    private UpdateUserUseCase updateUserUseCase;

    @MockBean
    private ListUserUseCase listUserUseCase;

    @MockBean
    private UserWebMapper mapper;

    @Test
    void shouldCreateUser() throws Exception {
        when(mapper.toCreateDto(any()))
                .thenReturn(mock(UserRequestDTO.class));

        when(createUserUseCase.execute(any()))
                .thenReturn(mock(UserResultDTO.class));

        when(mapper.toResponse(any()))
                .thenReturn(mock(UserResponsePayload.class));

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "name": "João",
                  "email": "joao@email.com",
                  "phone": "11999999999",
                  "password": "123456",
                  "userTypeId": "ADMIN"
                }
            """))
                .andExpect(status().isOk());
    }


    @Test
    void shouldReturnBadRequestWhenCreatePayloadInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldListUsersWithDefaultPagination() throws Exception {
        when(listUserUseCase.execute(any()))
                .thenReturn(new PageResultDTO<>(
                        List.of(),
                        0,
                        10,
                        0,
                        0
                ));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldListUsersWithPaginationParams() throws Exception {
        when(listUserUseCase.execute(any()))
                .thenReturn(new PageResultDTO<>(
                        List.of(),
                        1,
                        5,
                        0,
                        0
                ));

        mockMvc.perform(get("/api/v1/users")
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        when(mapper.toUpdateDto(any())).thenReturn(mock(UpdateUserDTO.class));
        when(updateUserUseCase.execute(any(), anyString()))
                .thenReturn(mock(UserResultDTO.class));
        when(mapper.toResponse(any()))
                .thenReturn(mock(UserResponsePayload.class));

        mockMvc.perform(patch("/api/v1/users/{id}", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "name": "Novo Nome"
                    }
                """))
                .andExpect(status().isOk());
    }



}
