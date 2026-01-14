package br.com.foodhub.infra.persistence.mongodb.mapper.user;

import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.association.UserAddress;
import br.com.foodhub.core.domain.entity.association.UserRestaurant;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.infra.persistence.mongodb.document.user.UserAddressDocument;
import br.com.foodhub.infra.persistence.mongodb.document.user.UserDocument;
import br.com.foodhub.infra.persistence.mongodb.document.user.UserRestaurantDocument;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected UserTypeGateway gateway;

    public User toDomain(UserDocument doc) {

        UserType userType = gateway.findById((doc.getUserTypeId()))
                .orElseThrow();

        User user = new User(
                doc.getName(),
                doc.getEmail(),
                doc.getPhone(),
                doc.getPassword(),
                userType,
                null
        );
        setId(user, doc.getId());

        if(doc.getAddresses() != null) {
            for ( UserAddressDocument ad : doc.getAddresses()) {
                UserAddress ua = new UserAddress(
                        user.getId(),
                        ad.getAddressBaseId(),
                        ad.getNumber(),
                        ad.getComplement(),
                        ad.isPrimary()
                );
                setId(ua, ad.getId());
                user.addAddress(ua);
            }
        }

        if (doc.getRestaurants() != null) {
            for (UserRestaurantDocument rd : doc.getRestaurants()) {
                UserRestaurant ur = new UserRestaurant(
                        user.getId(),
                        rd.getRestaurantId(),
                        rd.getUserTypeId()
                );
                setId(ur, rd.getId());
                user.addRestaurant(ur);
            }
        }

        return user;
    }

    public abstract UserDocument toDocument(User user);

    protected void setId(Object target, String id) {
        try {
            var field = target.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(target, id);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao setar ID via reflexão", e);
        }
    }


}
