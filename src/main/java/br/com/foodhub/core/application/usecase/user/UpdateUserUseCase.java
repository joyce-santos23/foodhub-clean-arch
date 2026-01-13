package br.com.foodhub.core.application.usecase.user;

import br.com.foodhub.core.application.dto.user.UpdateUserDTO;
import br.com.foodhub.core.application.dto.user.UserResultDTO;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserProfile;
import br.com.foodhub.core.domain.exceptions.generic.ResourceConflictException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserGateway gateway;

    public UserResultDTO execute(UpdateUserDTO dto, String id) {

        User user = gateway.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado"));

        if (dto.name() != null && !dto.name().isEmpty()) {
            user.changeName(dto.name());
        }
        if (dto.email() != null && !dto.email().isEmpty()) {
            String oldEmail = user.getEmail();
            user.changeEmail(dto.email());

            if (!user.getEmail().equals(oldEmail)) {
                if (gateway.existsByEmail(user.getEmail())) {
                    throw new ResourceConflictException("Email já cadastrado");
                }
            }
        }

        if (dto.phone() != null) {

            String oldPhone = user.getPhone();

            user.changePhone(dto.phone());

            if (!user.getPhone().equals(oldPhone)) {
                if (gateway.existsByPhone(user.getPhone())) {
                    throw new ResourceConflictException("Telefone já cadastrado.");
                }
            }
        }

        if (dto.customFields() != null) {
            user.updateProfile(new UserProfile(dto.customFields()));
        }

        UserResultDTO saved = gateway.save(user);

        return new UserResultDTO(
                saved.id(),
                saved.name(),
                saved.email(),
                saved.phone(),
                saved.cpf(),
                saved.userTypeId(),
                saved.attributes()
        );
    }
}
