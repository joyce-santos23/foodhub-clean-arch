package br.com.foodhub.infra.web.controller.restaurant.openinghour;

import br.com.foodhub.core.application.usecase.restaurant.openinghour.ChangeOpeningHoursUseCase;
import br.com.foodhub.core.application.usecase.restaurant.openinghour.ListOpeningHoursUseCase;
import br.com.foodhub.infra.web.api.restaurant.openinghour.OpeningHourApi;
import br.com.foodhub.infra.web.mapper.restaurant.openinghour.OpeningHoursWebMapper;
import br.com.foodhub.infra.web.payload.restaurant.openinghour.OpeningHoursResponsePayload;
import br.com.foodhub.infra.web.payload.restaurant.openinghour.UpdateOpeningHoursRequestPayload;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@Tag(name = "Restaurantes")
@RestController
@RequestMapping("api/v1/restaurants/{restaurantId}/opening-hours")
@RequiredArgsConstructor
public class OpeningHoursController implements OpeningHourApi {

    private final ChangeOpeningHoursUseCase changeUseCase;
    private final ListOpeningHoursUseCase listUseCase;
    private final OpeningHoursWebMapper mapper;

    @PutMapping("/{dayOfWeek}")
    public List<OpeningHoursResponsePayload> change(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String restaurantId,
            @PathVariable DayOfWeek dayOfWeek,
            @RequestBody @Valid UpdateOpeningHoursRequestPayload payload
    ) {
        changeUseCase.execute(
                userId,
                restaurantId,
                dayOfWeek,
                mapper.toDto(payload)
        );

        return listUseCase.execute(restaurantId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping
    public List<OpeningHoursResponsePayload> list(
            @PathVariable String restaurantId
    ) {
        return listUseCase.execute(restaurantId).stream()
                .map(mapper::toResponse)
                .toList();
    }
}

