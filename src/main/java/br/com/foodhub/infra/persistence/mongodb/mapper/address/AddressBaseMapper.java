package br.com.foodhub.infra.persistence.mongodb.mapper.address;

import br.com.foodhub.core.domain.entity.address.AddressBase;
import br.com.foodhub.infra.persistence.mongodb.document.address.AddressBaseDocument;
import org.springframework.stereotype.Component;

@Component
public class AddressBaseMapper {

    public AddressBase toDomain(AddressBaseDocument doc) {

        AddressBase address = AddressBase.reconstitute(
                doc.getId(),
                doc.getCep(),
                doc.getStreet(),
                doc.getNeighborhood(),
                doc.getCity(),
                doc.getState(),
                doc.getCountry()
        );
        return address;
    }

    public AddressBaseDocument toDocument(AddressBase domain) {
        if (domain == null) return null;

        AddressBaseDocument doc = new AddressBaseDocument();
        doc.setId(domain.getId());
        doc.setCep(domain.getCep());
        doc.setStreet(domain.getStreet());
        doc.setNeighborhood(domain.getNeighborhood());
        doc.setCity(domain.getCity());
        doc.setState(domain.getState());
        doc.setCountry(domain.getCountry());

        return doc;
    }


}
