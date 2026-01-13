package br.com.foodhub.core.application.port.address;

import br.com.foodhub.core.domain.entity.address.AddressBase;

import java.util.Optional;

public interface AddressBaseGateway {
    Optional<AddressBase> findByCep(String cep);
    AddressBase save(AddressBase address);
}
