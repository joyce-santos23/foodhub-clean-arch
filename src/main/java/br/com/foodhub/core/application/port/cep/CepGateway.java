package br.com.foodhub.core.application.port.cep;

import br.com.foodhub.core.domain.entity.address.AddressBase;

public interface CepGateway {
    AddressBase lookup(String cep);
}
