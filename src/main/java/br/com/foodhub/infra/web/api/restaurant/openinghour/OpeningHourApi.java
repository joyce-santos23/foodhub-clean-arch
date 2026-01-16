package br.com.foodhub.infra.web.api.restaurant.openinghour;

import br.com.foodhub.infra.web.payload.restaurant.openinghour.OpeningHoursResponsePayload;
import br.com.foodhub.infra.web.payload.restaurant.openinghour.UpdateOpeningHoursRequestPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RequestMapping("/api/v1/restaurants/{restaurantId}/opening-hours")
public interface OpeningHourApi {

    /* =========================
       Atualizar horário do dia
       ========================= */
    @Operation(summary = "Atualizar horário de funcionamento do restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Horário atualizado com sucesso")
    })
    @PutMapping("/{dayOfWeek}")
    List<OpeningHoursResponsePayload> change(
            @Parameter(
                    description = "ID do usuário que executa a ação",
                    required = true,
                    in = ParameterIn.HEADER
            )
            @RequestHeader("X-User-Id") String userId,

            @Parameter(description = "ID do restaurante")
            @PathVariable String restaurantId,

            @Parameter(description = "Dia da semana")
            @PathVariable DayOfWeek dayOfWeek,

            @RequestBody UpdateOpeningHoursRequestPayload payload
    );

    /* =========================
       Listar horários
       ========================= */
    @Operation(summary = "Listar horários de funcionamento do restaurante")
    @GetMapping
    List<OpeningHoursResponsePayload> list(
            @Parameter(description = "ID do restaurante")
            @PathVariable String restaurantId
    );
}
