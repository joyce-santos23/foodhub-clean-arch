package br.com.foodhub.infra.gateway.user;

import br.com.foodhub.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.infra.persistence.mongodb.mapper.user.UserMapper;
import br.com.foodhub.infra.persistence.mongodb.repository.user.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserGatewayImpl implements UserGateway {

    private final UserMongoRepository repository;
    private final UserMapper mapper;

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return repository.existsByPhone(phone);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }

    @Override
    public User save(User user) {
        var document = mapper.toDocument(user);
        var result = repository.save(document);

        return mapper.toDomain(result);
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public PageResultDTO<User> findAll(int page, int size) {
        var pageable = PageRequest.of(page, size);
        var result = repository.findAll(pageable);
        var users = result.getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();
        return new PageResultDTO<>(
                users,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    public boolean existsUserWithUserType(String userTypeId) {
        return repository.existsByUserTypeId(userTypeId);
    }
}
