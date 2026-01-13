package br.com.foodhub.core.application.usecase.user.usertype;

import br.com.foodhub.core.application.dto.user.usertype.UpdateUserTypeDTO;
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

    public UserTypeResultDTO execute(UpdateUserTypeDTO dto) {

        UserType type = gateway.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de usuário não encontrado com o ID: " + dto.id())
                );
        type.rename(dto.name());

        gateway.save(type);

        return new UserTypeResultDTO(
                type.getId(),
                type.getName(),
                type.isRestaurantRelated()
        );
    }
}
