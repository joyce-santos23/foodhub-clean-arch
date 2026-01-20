package br.com.foodhub.infra.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerIT extends AbstractIntegrationTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String userTypeId;

    /**
     * Pré-condição real:
     * todo usuário precisa de um userType existente
     */
    @BeforeEach
    void setup() throws Exception {
        String payload = """
        {
          "name": "EMPLOYEE"
        }
        """;

        String response =
                mockMvc.perform(post("/api/v1/user-types")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        userTypeId = objectMapper.readTree(response).get("id").asText();
    }

    @Test
    void shouldCreateUserSuccessfully() throws Exception {

        String payload = """
        {
          "name": "João da Silva",
          "email": "joao@email.com",
          "phone": "41999999999",
          "password": "senha123",
          "cpf": "12345678909",
          "userTypeId": "%s"
        }
        """.formatted(userTypeId);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("João da Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.userTypeId").value(userTypeId));
    }

    @Test
    void shouldListUsersWithPagination() throws Exception {

        // cria usuário antes
        shouldCreateUserSuccessfully();

        mockMvc.perform(get("/api/v1/users")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10));
    }

    @Test
    void shouldUpdateUserSuccessfully() throws Exception {

        // cria usuário
        String createPayload = """
        {
          "name": "João",
          "email": "joao@email.com",
          "phone": "41999999999",
          "password": "senha123",
          "userTypeId": "%s"
        }
        """.formatted(userTypeId);

        String response =
                mockMvc.perform(post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createPayload))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        String userId = objectMapper.readTree(response).get("id").asText();

        String updatePayload = """
        {
          "name": "João Atualizado",
          "email": "joao.atualizado@email.com"
        }
        """;

        mockMvc.perform(patch("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Atualizado"))
                .andExpect(jsonPath("$.email").value("joao.atualizado@email.com"));
    }

    @Test
    void shouldReturn422WhenEmailInvalid() throws Exception {

        String payload = """
        {
          "name": "João",
          "email": "email-invalido",
          "phone": "41999999999",
          "password": "123456",
          "userTypeId": "%s"
        }
        """.formatted(userTypeId);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistingUser() throws Exception {

        String payload = """
        {
          "name": "Novo Nome"
        }
        """;

        mockMvc.perform(patch("/api/v1/users/{id}", "ID_INEXISTENTE")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound());
    }
}
