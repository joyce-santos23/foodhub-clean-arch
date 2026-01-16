package br.com.foodhub.core.application.port.user;

import br.com.foodhub.core.domain.entity.user.UserType;

import java.util.List;
import java.util.Optional;

public interface UserTypeGateway {

    Optional<UserType> findById(String id);

    UserType save(UserType userType);

    List<UserType> findAll();

    void delete(String userTypeId);

    Optional<UserType> findByName(String owner);
}
