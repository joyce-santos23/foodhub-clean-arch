package br.com.foodhub.core.application.usecase.address;

import br.com.foodhub.core.application.port.cep.CepGateway;
import br.com.foodhub.core.application.port.address.AddressBaseGateway;
import br.com.foodhub.core.domain.entity.address.AddressBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindOrCreateAddressBaseUseCase {

    private final AddressBaseGateway gateway;
    private final CepGateway cepGateway;

    public AddressBase execute(String cep) {

        String cepForSearch = cep.replaceAll("[^0-9]", "");

        return gateway.findByCep(cepForSearch)
                .orElseGet(() -> {
                    AddressBase fromApi = cepGateway.lookup(cepForSearch);
                    return gateway.save(fromApi);
                });
    }
}
