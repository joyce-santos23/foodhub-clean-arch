package br.com.foodhub.core.domain.exceptions.user;

public class AddressNotBelongsToUserException extends RuntimeException {
    public AddressNotBelongsToUserException() {
        super("Este endereço não pertence a este usuário");
    }
}
