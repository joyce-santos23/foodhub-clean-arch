package br.com.foodhub.core.application.dto.restaurant.openingHour;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record OpeningHoursDTO(
        DayOfWeek dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime,
        boolean closed
) {}

