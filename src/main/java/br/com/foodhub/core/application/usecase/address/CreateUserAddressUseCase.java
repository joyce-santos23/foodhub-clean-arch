package br.com.foodhub.core.application.usecase.address;

import br.com.foodhub.core.application.dto.address.UserAddressDTO;
import br.com.foodhub.core.application.dto.address.UserAddressResultDTO;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.address.AddressBase;
import br.com.foodhub.core.domain.entity.association.UserAddress;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserAddressUseCase {

    private final FindOrCreateAddressBaseUseCase findOrCreateAddressBaseUseCase;
    private final UserGateway userGateway;

    public UserAddressResultDTO execute(UserAddressDTO requestDTO) {
        User user = userGateway.findById(requestDTO.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + requestDTO.userId()));

        AddressBase addressBase = findOrCreateAddressBaseUseCase.execute(requestDTO.cep());

        UserAddress userAddress = new UserAddress(
                user.getId(),
                addressBase.getId(),
                requestDTO.number(),
                requestDTO.complement(),
                requestDTO.primary()
        );
        user.addAddress(userAddress);
        userGateway.save(user);

        return new UserAddressResultDTO(
                userAddress.getId(),
                userAddress.getUserId(),
                userAddress.getAddressId(),
                userAddress.isPrimary(),
                userAddress.getNumber(),
                userAddress.getComplement()
        );
    }

}
