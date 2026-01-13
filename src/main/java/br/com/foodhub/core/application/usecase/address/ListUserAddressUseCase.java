package br.com.foodhub.core.application.usecase.address;

import br.com.foodhub.core.application.dto.address.UserAddressResultDTO;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListUserAddressUseCase {

    private final UserGateway gateway;

    public List<UserAddressResultDTO> execute(UserAddressResultDTO dto) {

        User user = gateway.findById(dto.user())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + dto.user()));

        return user.getAddresses().stream()
                .map(address -> new UserAddressResultDTO(
                        address.getId(),
                        address.getUserId(),
                        address.getAddressId(),
                        address.isPrimary(),
                        address.getNumber(),
                        address.getComplement()
                ))
                .toList();
    }
}
