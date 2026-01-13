package br.com.foodhub.core.application.usecase.address;

import br.com.foodhub.core.application.dto.address.DeleteUserAddressDTO;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import br.com.foodhub.core.domain.exceptions.user.AddressNotBelongsToUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserAddressUseCase {

    private UserGateway gateway;

    public void execute(DeleteUserAddressDTO dto) {

        User user = gateway.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        boolean removed = user.getAddresses().removeIf(
                address -> address.getId().equals(dto.userAddressId())
        );

        if (!removed) {
            throw new AddressNotBelongsToUserException();
        }

        gateway.save(user);
    }
}
