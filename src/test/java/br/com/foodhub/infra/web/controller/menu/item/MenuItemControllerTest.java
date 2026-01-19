package br.com.foodhub.infra.web.controller.menu.items;

import br.com.foodhub.core.application.usecase.menu.items.CreateMenuItemUseCase;
import br.com.foodhub.core.application.usecase.menu.items.DeleteMenuItemUseCase;
import br.com.foodhub.core.application.usecase.menu.items.ListMenuItemUseCase;
import br.com.foodhub.core.application.usecase.menu.items.UpdateMenuItemUseCase;
import br.com.foodhub.infra.web.mapper.menu.items.MenuItemWebMapper;
import br.com.foodhub.infra.web.payload.menu.items.MenuItemResponsePayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MenuItemController.class)
@AutoConfigureMockMvc(addFilters = false)
class MenuItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateMenuItemUseCase createUseCase;

    @MockBean
    private UpdateMenuItemUseCase updateUseCase;

    @MockBean
    private DeleteMenuItemUseCase deleteUseCase;

    @MockBean
    private ListMenuItemUseCase listUseCase;

    @MockBean
    private MenuItemWebMapper mapper;


    @Test
    void shouldCreateMenuItem() throws Exception {
        when(createUseCase.execute(anyString(), anyString(), anyString(), any()))
                .thenReturn(null);

        when(mapper.toResponse(any()))
                .thenReturn(mock(MenuItemResponsePayload.class));

        mockMvc.perform(post("/api/v1/restaurants/r1/menus/m1/items")
                        .header("X-User-Id", "user-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Hambúrguer",
                                  "price": 25.90
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenHeaderMissingOnCreate() throws Exception {
        mockMvc.perform(post("/api/v1/restaurants/r1/menus/m1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn422WhenCreatePayloadInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/restaurants/r1/menus/m1/items")
                        .header("X-User-Id", "user-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": null
                                }
                                """))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void shouldUpdateMenuItem() throws Exception {
        when(updateUseCase.execute(anyString(), anyString(), anyString(), anyString(), any()))
                .thenReturn(null);

        when(mapper.toResponse(any()))
                .thenReturn(mock(MenuItemResponsePayload.class));

        mockMvc.perform(put("/api/v1/restaurants/r1/menus/m1/items/i1")
                        .header("X-User-Id", "user-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Hambúrguer Especial",
                                  "price": 29.90
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteMenuItem() throws Exception {
        doNothing().when(deleteUseCase)
                .execute(anyString(), anyString(), anyString(), anyString());

        mockMvc.perform(delete("/api/v1/restaurants/r1/menus/m1/items/i1")
                        .header("X-User-Id", "user-1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetMenuItemById() throws Exception {
        when(listUseCase.execute(anyString(), anyString(), anyString()))
                .thenReturn(null);

        when(mapper.toResponse(any()))
                .thenReturn(mock(MenuItemResponsePayload.class));

        mockMvc.perform(get("/api/v1/restaurants/r1/menus/m1/items/i1"))
                .andExpect(status().isOk());
    }
}
