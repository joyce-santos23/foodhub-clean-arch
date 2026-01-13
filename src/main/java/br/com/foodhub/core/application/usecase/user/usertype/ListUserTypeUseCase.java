package br.com.foodhub.core.application.usecase.user.usertype;

import br.com.foodhub.core.application.dto.user.usertype.UserTypeResultDTO;
import br.com.foodhub.core.application.port.user.UserTypeGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListUserTypeUseCase {

    private final UserTypeGateway gateway;

    public List<UserTypeResultDTO> execute() {

        return gateway.findAll().stream()
                .map(type -> new UserTypeResultDTO(
                        type.getId(),
                        type.getName(),
                        type.isRestaurantRelated()
                ))
                .toList();
    }
}
