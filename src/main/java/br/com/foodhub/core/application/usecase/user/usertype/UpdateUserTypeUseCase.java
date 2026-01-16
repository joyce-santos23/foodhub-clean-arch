package br.com.foodhub.core.application.usecase.user.usertype;

import br.com.foodhub.core.application.dto.user.usertype.UserTypeRequestDTO;
import br.com.foodhub.core.application.dto.user.usertype.UserTypeResultDTO;
import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserTypeUseCase {

    private final UserTypeGateway gateway;

    public UserTypeResultDTO execute(String id, UserTypeRequestDTO dto) {

        UserType type = gateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de usuário não encontrado com o ID: " + id)
                );
        type.rename(dto.name());

        gateway.save(type);

        return UserTypeResultDTO.from(type);
    }
}
