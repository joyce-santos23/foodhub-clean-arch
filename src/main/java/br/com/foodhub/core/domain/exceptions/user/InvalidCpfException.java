package br.com.foodhub.core.domain.exceptions.user;

public class InvalidCpfException extends RuntimeException {
    public InvalidCpfException(String cpfInválido) {
        super();
    }
}
