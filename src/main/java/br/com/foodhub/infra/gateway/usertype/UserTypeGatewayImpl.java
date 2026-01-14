package br.com.foodhub.infra.gateway.usertype;

import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.infra.persistence.mongodb.mapper.usertype.UserTypeMapper;
import br.com.foodhub.infra.persistence.mongodb.repository.usertype.UserTypeMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserTypeGatewayImpl implements UserTypeGateway {

    private final UserTypeMongoRepository repository;
    private final UserTypeMapper mapper;

    @Override
    public Optional<UserType> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public UserType save(UserType userType) {
        return mapper.toDomain(
                repository.save(
                        mapper.toDocument(userType)
                )
        );
    }

    @Override
    public List<UserType> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByUserTypeId(String userTypeId) {
        return repository.existsById(userTypeId);
    }

    @Override
    public void delete(String userTypeId) {
        repository.deleteById(userTypeId);
    }
}
