package br.com.foodhub.infra.web.controller.menu;

import br.com.foodhub.core.application.dto.menu.MenuResultDTO;
import br.com.foodhub.core.application.usecase.menu.CreateMenuUseCase;
import br.com.foodhub.core.application.usecase.menu.DeleteMenuUseCase;
import br.com.foodhub.core.application.usecase.menu.ListRestaurantMenuUseCase;
import br.com.foodhub.infra.web.mapper.menu.MenuWebMapper;
import br.com.foodhub.infra.web.payload.menu.CreateMenuRequestPayload;
import br.com.foodhub.infra.web.payload.menu.MenuResponsePayload;
import br.com.foodhub.infra.web.payload.menu.MenuWithItemsResponsePayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuController.class)
@AutoConfigureMockMvc(addFilters = false)
class MenuControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateMenuUseCase createMenuUseCase;

    @MockBean
    private DeleteMenuUseCase deleteMenuUseCase;

    @MockBean
    private ListRestaurantMenuUseCase listRestaurantMenuUseCase;

    @MockBean
    private MenuWebMapper mapper;


    @Test
    void shouldCreateMenu() throws Exception {
        CreateMenuRequestPayload payload =
                new CreateMenuRequestPayload("Menu Principal");

        MenuResultDTO resultDTO = new MenuResultDTO(
                "Menu Principal",
                "restaurant-1"
        );

        MenuResponsePayload response =
                new MenuResponsePayload("menu-1", "Menu Principal");

        when(mapper.toCreateDto(any())).thenReturn(null);
        when(createMenuUseCase.execute(eq("user-1"), eq("restaurant-1"), any()))
                .thenReturn(resultDTO);
        when(mapper.toResponse(resultDTO)).thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/restaurants/restaurant-1/menus")
                                .header("X-User-Id", "user-1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(payload))
                )
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenHeaderMissingOnCreate() throws Exception {
        CreateMenuRequestPayload payload =
                new CreateMenuRequestPayload("Menu Principal");

        mockMvc.perform(
                        post("/api/v1/restaurants/restaurant-1/menus")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(payload))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn422WhenCreatePayloadInvalid() throws Exception {
        CreateMenuRequestPayload payload =
                new CreateMenuRequestPayload(null);

        mockMvc.perform(
                        post("/api/v1/restaurants/restaurant-1/menus")
                                .header("X-User-Id", "user-1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(payload))
                )
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void shouldListMenus() throws Exception {
        MenuWithItemsResponsePayload menu =
                new MenuWithItemsResponsePayload(
                        "menu-1",
                        "Menu Principal",
                        List.of()
                );

        when(listRestaurantMenuUseCase.execute("restaurant-1"))
                .thenReturn(List.of());
        when(mapper.toWithItemsResponse(any()))
                .thenReturn(menu);

        mockMvc.perform(
                        get("/api/v1/restaurants/restaurant-1/menus")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteMenu() throws Exception {
        doNothing().when(deleteMenuUseCase)
                .execute("user-1", "restaurant-1", "menu-1");

        mockMvc.perform(
                        delete("/api/v1/restaurants/restaurant-1/menus/menu-1")
                                .header("X-User-Id", "user-1")
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn400WhenHeaderMissingOnDelete() throws Exception {
        mockMvc.perform(
                        delete("/api/v1/restaurants/restaurant-1/menus/menu-1")
                )
                .andExpect(status().isBadRequest());
    }
}
