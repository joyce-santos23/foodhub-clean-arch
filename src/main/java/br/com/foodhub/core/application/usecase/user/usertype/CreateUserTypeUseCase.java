package br.com.foodhub.core.application.usecase.user.usertype;

import br.com.foodhub.core.application.dto.user.usertype.UserTypeRequestDTO;
import br.com.foodhub.core.application.dto.user.usertype.UserTypeResultDTO;
import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.user.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserTypeUseCase {

    private final UserTypeGateway gateway;

    public UserTypeResultDTO execute(UserTypeRequestDTO dto) {

        UserType userType = new UserType(dto.name());

        UserType saved = gateway.save(userType);

        return UserTypeResultDTO.from(saved);
    }
}
