package br.com.foodhub.core.application.port.security;

public interface PasswordHasherGateway {
    String hash(String rawPassword);
}
