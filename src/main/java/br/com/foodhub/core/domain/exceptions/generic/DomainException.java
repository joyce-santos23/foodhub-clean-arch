package br.com.foodhub.core.domain.exceptions.generic;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
