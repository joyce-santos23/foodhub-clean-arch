package br.com.foodhub.infra.web.mapper.restaurant.openinghour;

import br.com.foodhub.core.application.dto.restaurant.openingHour.OpeningHoursResultDTO;
import br.com.foodhub.core.application.dto.restaurant.openingHour.UpdateOpeningHoursDTO;
import br.com.foodhub.infra.web.payload.restaurant.openinghour.OpeningHoursResponsePayload;
import br.com.foodhub.infra.web.payload.restaurant.openinghour.UpdateOpeningHoursRequestPayload;
import org.mapstruct.Mapper;

import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface OpeningHoursWebMapper {

    default UpdateOpeningHoursDTO toDto(UpdateOpeningHoursRequestPayload payload) {

        if (payload.closed()) {
            return new UpdateOpeningHoursDTO(
                    null,
                    null,
                    true
            );
        }

        return new UpdateOpeningHoursDTO(
                LocalTime.parse(payload.openingTime()),
                LocalTime.parse(payload.closingTime()),
                payload.closed()
        );
    }

    OpeningHoursResponsePayload toResponse(
            OpeningHoursResultDTO dto
    );
}

