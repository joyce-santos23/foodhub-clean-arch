package br.com.foodhub.infra.config.initializer;

import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        UserType ownerType = userTypeGateway
                .findByName("OWNER")
                .orElseGet(() ->
                        userTypeGateway.save(
                                new UserType("OWNER"))
                );

        UserType customerType = userTypeGateway
                .findByName("CUSTOMER")
                .orElseGet(() ->
                        userTypeGateway.save(
                                new UserType("CUSTOMER"))
                );


        createUserIfNotExists(
                "owner@foodhub.com",
                "Owner User",
                "11999990001",
                "owner123",
                ownerType
        );

        createUserIfNotExists(
                "customer@foodhub.com",
                "Customer User",
                "11999990002",
                "customer123",
                customerType
        );
    }

    private void createUserIfNotExists(
            String email,
            String name,
            String phone,
            String rawPassword,
            UserType type
    ) {
        if (userGateway.existsByEmail(email)) {
            return;
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(
                name,
                email,
                phone,
                hashedPassword,
                type
        );

        userGateway.save(user);
    }
}
