package br.com.foodhub.infra.persistence.mongodb.mapper.address;

import br.com.foodhub.core.domain.entity.address.AddressBase;
import br.com.foodhub.infra.persistence.mongodb.document.address.AddressBaseDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AddressBaseMapper {

    public AddressBase toDomain(AddressBaseDocument doc) {

        AddressBase address = new AddressBase(
                doc.getCep(),
                doc.getStreet(),
                doc.getNeighborhood(),
                doc.getCity(),
                doc.getState(),
                doc.getCountry()
        );
        setId(address, doc.getId());
        return address;
    }

    public abstract AddressBaseDocument toDocument(AddressBase domain);

    protected void setId(AddressBase address, String id) {
        try {
            var field = AddressBase.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(address, id);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao setar id do addressBase ", e);
        }
    }
}
