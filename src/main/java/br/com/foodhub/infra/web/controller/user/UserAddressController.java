package br.com.foodhub.infra.web.controller.user;

import br.com.foodhub.core.application.dto.address.UpdateUserAddressDTO;
import br.com.foodhub.core.application.dto.address.UserAddressDTO;
import br.com.foodhub.core.application.dto.address.UserAddressResultDTO;
import br.com.foodhub.core.application.usecase.address.CreateUserAddressUseCase;
import br.com.foodhub.core.application.usecase.address.DeleteUserAddressUseCase;
import br.com.foodhub.core.application.usecase.address.ListUserAddressUseCase;
import br.com.foodhub.core.application.usecase.address.UpdateUserAddressUseCase;
import br.com.foodhub.infra.web.api.user.UserAddressApi;
import br.com.foodhub.infra.web.mapper.user.UserAddressMapper;
import br.com.foodhub.infra.web.payload.address.UpdateUserAddressRequestPayload;
import br.com.foodhub.infra.web.payload.address.UserAddressRequestPayload;
import br.com.foodhub.infra.web.payload.address.UserAddressResponsePayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/{userId}/addresses")
@RequiredArgsConstructor
public class UserAddressController implements UserAddressApi {

    private final CreateUserAddressUseCase createUserAddressUseCase;
    private final UpdateUserAddressUseCase updateUserAddressUseCase;
    private final ListUserAddressUseCase listUserAddressUseCase;
    private final DeleteUserAddressUseCase deleteUserAddressUseCase;
    private final UserAddressMapper mapper;

    @GetMapping
    public List<UserAddressResponsePayload> listAllAddress(
            @PathVariable String userId) {

        return listUserAddressUseCase.execute(userId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @PostMapping
    public UserAddressResponsePayload create(
            @PathVariable String userId,
            @RequestBody @Valid UserAddressRequestPayload payload) {
        UserAddressDTO dto = mapper.toCreateDto(payload);

        UserAddressResultDTO result = createUserAddressUseCase.execute(userId, dto);

        return mapper.toResponse(result);
    }

    @PutMapping("/{userAddressId}")
    public UserAddressResponsePayload update(
            @PathVariable String userId,
            @PathVariable String userAddressId,
            @RequestBody @Valid UpdateUserAddressRequestPayload payload) {

        UpdateUserAddressDTO dto = mapper.toUpdateDto(payload);

        UserAddressResultDTO result = updateUserAddressUseCase.execute(userId, userAddressId, dto);

        return mapper.toResponse(result);
    }

    @DeleteMapping("/{userAddressId}")
    public void delete(
            @PathVariable String userId,
            @PathVariable String userAddressId) {
        deleteUserAddressUseCase.execute(userId, userAddressId);
    }

}
