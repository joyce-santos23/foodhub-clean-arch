package br.com.foodhub.core.application.usecase.address;

import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import br.com.foodhub.core.domain.exceptions.user.AddressNotBelongsToUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserAddressUseCase {

    private final UserGateway gateway;

    public void execute(String userId, String userAddressId) {

        User user = gateway.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        boolean removed = user.getAddresses().removeIf(
                address -> address.getId().equals(userAddressId)
        );

        if (!removed) {
            throw new AddressNotBelongsToUserException();
        }

        gateway.save(user);
    }
}
