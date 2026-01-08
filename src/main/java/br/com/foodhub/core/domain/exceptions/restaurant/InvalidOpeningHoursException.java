package br.com.foodhub.core.domain.exceptions.restaurant;

public class InvalidOpeningHoursException extends RuntimeException {
    public InvalidOpeningHoursException(String horaDeAberturaInvalida) {
        super(horaDeAberturaInvalida);
    }
}
