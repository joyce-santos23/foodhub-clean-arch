package br.com.foodhub.infra.web.mapper.user;

import br.com.foodhub.core.application.dto.address.UpdateUserAddressDTO;
import br.com.foodhub.core.application.dto.address.UserAddressDTO;
import br.com.foodhub.core.application.dto.address.UserAddressResultDTO;
import br.com.foodhub.core.application.port.address.AddressBaseGateway;
import br.com.foodhub.core.domain.entity.address.AddressBase;
import br.com.foodhub.infra.web.payload.address.AddressBaseResponsePayload;
import br.com.foodhub.infra.web.payload.address.UpdateUserAddressRequestPayload;
import br.com.foodhub.infra.web.payload.address.UserAddressRequestPayload;
import br.com.foodhub.infra.web.payload.address.UserAddressResponsePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAddressMapper {

    private final AddressBaseGateway addressBaseGateway;

    public UserAddressDTO toCreateDto(UserAddressRequestPayload payload) {
        return new UserAddressDTO(
                payload.cep(),
                payload.number(),
                payload.complement(),
                payload.primary()
        );
    }

    public UpdateUserAddressDTO toUpdateDto(UpdateUserAddressRequestPayload payload) {
        return new UpdateUserAddressDTO(
                payload.number(),
                payload.complement(),
                payload.primary()
        );
    }

    public UserAddressResponsePayload toResponse(UserAddressResultDTO dto) {

        AddressBase address = addressBaseGateway.findById(dto.addressBaseId())
                .orElseThrow();

        return new UserAddressResponsePayload(
                dto.id(),
                dto.primary(),
                dto.number(),
                dto.complement(),
                new AddressBaseResponsePayload(
                        address.getCep(),
                        address.getStreet(),
                        address.getNeighborhood(),
                        address.getCity(),
                        address.getState(),
                        address.getCountry()
                )
        );
    }
}
