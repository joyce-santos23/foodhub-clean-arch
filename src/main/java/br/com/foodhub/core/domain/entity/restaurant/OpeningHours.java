package br.com.foodhub.core.domain.entity.restaurant;

import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import br.com.foodhub.core.domain.exceptions.restaurant.InvalidOpeningHoursException;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
public class OpeningHours {
    private final DayOfWeek dayOfWeek;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final boolean closed;


    public OpeningHours(
            DayOfWeek dayOfWeek,
            LocalTime openTime,
            LocalTime closeTime,
            boolean closed
    ) {
        this.dayOfWeek = require(dayOfWeek, "Dia da semana");
        this.closed = closed;

        if (closed) {
            if (openTime != null || closeTime != null) {
                throw new InvalidOpeningHoursException("Hora de abertura inválida");
            }
            this.openTime = null;
            this.closeTime = null;
        } else {
            this.openTime = require(openTime, "Abertura");
            this.closeTime = require(closeTime, "Fechamento");

            if (!openTime.isBefore(closeTime)) {
                throw new InvalidOpeningHoursException("Horário de abertura deve ser anterior ao horário de fechamento");
            }
        }
    }

    public boolean isOpenAt(LocalTime time) {
        if (closed) return false;
        return !time.isBefore(openTime) && time.isBefore(closeTime);
    }

    private <T> T require(T value, String field) {
        if (value == null) {
            throw new RequiredFieldException(field);
        }
        return value;
    }
}
