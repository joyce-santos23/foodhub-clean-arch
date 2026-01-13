package br.com.foodhub.core.application.usecase.user;

import br.com.foodhub.core.application.dto.pagination.PageRequestDTO;
import br.com.foodhub.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.core.application.dto.user.UserResultDTO;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ListUserUseCase {

    private final UserGateway gateway;

    public PageResultDTO<UserResultDTO> execute(PageRequestDTO dto) {

        PageResultDTO<User> page = gateway.findAll(dto.page(), dto.size());

        List<UserResultDTO> result = page.content().stream()
                .map(user -> new UserResultDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getCpf(),
                        user.getUserType().getId(),
                        (Map<String, Object>) user.getAttributes()
                )).toList();

        return new PageResultDTO<>(
                result,
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages()
        );
    }
}
