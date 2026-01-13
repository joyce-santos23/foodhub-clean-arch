package br.com.foodhub.core.application.usecase.user.usertype;

import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserTypeUseCase {

    private final UserTypeGateway gateway;

    public void execute(String userTypeId) {

        UserType type = gateway.findById(userTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado.")
                );

        if (type.isSystemType()) {
            throw new BusinessRuleViolationException("Não é permitido excluir tipos de usuário do sistema."
            );
        }

        if (gateway.existsByUserTypeId(userTypeId)) {
            throw new BusinessRuleViolationException("Não é permitido excluir tipos de usuário vinculados a usuários."
            );
        }
        gateway.delete(userTypeId);
    }
}
