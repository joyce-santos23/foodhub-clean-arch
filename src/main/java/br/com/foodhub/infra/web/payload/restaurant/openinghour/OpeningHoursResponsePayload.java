package br.com.foodhub.infra.web.payload.restaurant.openinghour;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Schema(description = "Horário de funcionamento do restaurante")
public record OpeningHoursResponsePayload(

        @Schema(example = "MONDAY")
        DayOfWeek dayOfWeek,

        @Schema(example = "08:00")
        @JsonFormat(pattern = "HH:mm")
        String openingTime,

        @Schema(example = "18:00")
        @JsonFormat(pattern = "HH:mm")
        String closingTime,

        @Schema(example = "false")
        boolean closed
) {}
