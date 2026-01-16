package br.com.foodhub.core.application.dto.restaurant.openingHour;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record OpeningHoursResultDTO(
        DayOfWeek dayOfWeek,
        LocalTime openingTime,
        LocalTime closingTime,
        boolean closed
) {}

