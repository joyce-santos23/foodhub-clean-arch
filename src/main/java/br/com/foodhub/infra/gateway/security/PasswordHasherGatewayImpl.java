package br.com.foodhub.infra.gateway.security;

import br.com.foodhub.core.application.port.security.PasswordHasherGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordHasherGatewayImpl implements PasswordHasherGateway {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String hash(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
