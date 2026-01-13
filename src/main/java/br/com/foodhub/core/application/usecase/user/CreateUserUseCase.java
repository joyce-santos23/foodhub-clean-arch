package br.com.foodhub.core.application.usecase.user;

import br.com.foodhub.core.application.dto.user.UserRequestDTO;
import br.com.foodhub.core.application.dto.user.UserResultDTO;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserProfile;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.ResourceConflictException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;

    public UserResultDTO execute(UserRequestDTO dto) {

        UserType userType = userTypeGateway.findById(dto.userTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário inválido."));

        UserProfile attributes = dto.customFields() != null && !dto.customFields().isEmpty()
                ? new UserProfile(dto.customFields())
                : UserProfile.empty();

        User user = new User(
                dto.name(),
                dto.email(),
                dto.phone(),
                dto.password(),
                userType,
                attributes
        );

        if (userGateway.existsByEmail(user.getEmail())) {
            throw new ResourceConflictException("Email já cadastrado.");
        }

        if (userGateway.existsByPhone(user.getPhone())) {
            throw new ResourceConflictException("Telefone já cadastrado.");
        }

        if (dto.cpf() != null && !dto.cpf().isBlank()) {
            user.defineCpf(dto.cpf());
            if (userGateway.existsByCpf(user.getCpf())) {
                throw new ResourceConflictException("CPF já cadastrado.");
            }
        }

        return userGateway.save(user);
    }
}
