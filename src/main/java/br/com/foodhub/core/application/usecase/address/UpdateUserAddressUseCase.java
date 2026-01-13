package br.com.foodhub.core.application.usecase.address;

import br.com.foodhub.core.application.dto.address.UpdateUserAddressDTO;
import br.com.foodhub.core.application.dto.address.UserAddressResultDTO;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.association.UserAddress;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import br.com.foodhub.core.domain.exceptions.user.AddressNotBelongsToUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserAddressUseCase {

    private final UserGateway gateway;

    public UserAddressResultDTO execute(UpdateUserAddressDTO dto) {

        User user = gateway.findById(dto.userAddressId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado com ID: " + dto.userAddressId()
                ));

        UserAddress address = user.getAddresses().stream()
                .filter(a -> a.getId().equals(dto.userAddressId()))
                .findFirst()
                .orElseThrow(AddressNotBelongsToUserException::new);

        address.update(
                dto.number(),
                dto.complement()
        );

        if (address.isPrimary()) {
            user.definePrimaryAddress(address.getId());
        }
        gateway.save(user);

        return new UserAddressResultDTO(
                address.getId(),
                address.getUserId(),
                address.getAddressId(),
                address.isPrimary(),
                address.getNumber(),
                address.getComplement()
        );
    }
}
