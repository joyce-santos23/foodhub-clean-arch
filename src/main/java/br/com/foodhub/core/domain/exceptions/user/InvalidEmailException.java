package br.com.foodhub.core.domain.exceptions.user;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String emailInválido) {
        super();
    }
}
