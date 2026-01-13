package br.com.foodhub.core.application.dto.restaurant.openingHour;

import java.util.List;

public record ChangeOpeningHoursDTO(
        String restaurantId,
        List<OpeningHoursDTO> openingHours
) {}

