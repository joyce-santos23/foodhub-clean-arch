package br.com.foodhub.infra.persistence.mongodb.mapper.user;

import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.association.UserAddress;
import br.com.foodhub.core.domain.entity.association.UserRestaurant;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.infra.persistence.mongodb.document.user.UserAddressDocument;
import br.com.foodhub.infra.persistence.mongodb.document.user.UserDocument;
import br.com.foodhub.infra.persistence.mongodb.document.user.UserRestaurantDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    protected UserTypeGateway gateway;

    public User toDomain(UserDocument doc) {

        UserType userType = gateway.findById((doc.getUserTypeId()))
                .orElseThrow();

        User user = User.reconstitute(
                doc.getId(),
                doc.getName(),
                doc.getEmail(),
                doc.getPhone(),
                doc.getCpf(),
                doc.getPassword(),
                userType
        );

        if(doc.getAddresses() != null) {
            for ( UserAddressDocument ad : doc.getAddresses()) {
                UserAddress ua = UserAddress.reconstitute(
                        ad.getId(),
                        user.getId(),
                        ad.getAddressBaseId(),
                        ad.getNumber(),
                        ad.getComplement(),
                        ad.isPrimary()
                );
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
                user.addRestaurant(ur);
            }
        }

        return user;
    }

    public UserDocument toDocument(User user) {
        UserDocument doc = new UserDocument();

        doc.setId(user.getId());
        doc.setName(user.getName());
        doc.setEmail(user.getEmail());
        doc.setPhone(user.getPhone());
        doc.setCpf(user.getCpf());
        doc.setPassword(user.getPassword());

        // 🔥 ESSENCIAL
        doc.setUserTypeId(user.getUserType().getId());

        // addresses
        if (user.getAddresses() != null) {
            doc.setAddresses(
                    user.getAddresses().stream()
                            .map(a -> new UserAddressDocument(
                                    a.getId(),
                                    a.getAddressId(),
                                    a.getNumber(),
                                    a.getComplement(),
                                    a.isPrimary()
                            ))
                            .toList()
            );
        }

        // restaurants
        if (user.getRestaurants() != null) {
            doc.setRestaurants(
                    user.getRestaurants().stream()
                            .map(r -> new UserRestaurantDocument(
                                    r.getRestaurantId(),
                                    r.getUserTypeId()
                            ))
                            .toList()
            );
        }

        return doc;
    }
}

