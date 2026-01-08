package br.com.foodhub.core.domain.exceptions.restaurant;

public class InvalidCnpjException extends RuntimeException {
    public InvalidCnpjException() {
        super("CNPJ inválido.");
    }
}
