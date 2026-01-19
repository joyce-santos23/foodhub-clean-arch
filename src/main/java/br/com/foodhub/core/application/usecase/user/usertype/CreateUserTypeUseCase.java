package br.com.foodhub.core.application.usecase.user.usertype;

import br.com.foodhub.core.application.dto.user.usertype.UserTypeRequestDTO;
import br.com.foodhub.core.application.dto.user.usertype.UserTypeResultDTO;
import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserTypeUseCase {

    private final UserTypeGateway gateway;

    public UserTypeResultDTO execute(UserTypeRequestDTO dto) {

        String normalizedName = dto.name().trim().toUpperCase();

        if (gateway.existsByName(normalizedName)) {
            throw new BusinessRuleViolationException(
                    "Já existe um tipo de usuário com esse nome"
            );
        }

        UserType userType = new UserType(normalizedName);

        UserType saved = gateway.save(userType);

        return UserTypeResultDTO.from(saved);
    }
}
