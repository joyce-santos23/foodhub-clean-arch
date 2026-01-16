package br.com.foodhub.infra.web.controller.user;

import br.com.foodhub.core.application.dto.pagination.PageRequestDTO;
import br.com.foodhub.core.application.usecase.user.CreateUserUseCase;
import br.com.foodhub.core.application.usecase.user.ListUserUseCase;
import br.com.foodhub.core.application.usecase.user.UpdateUserUseCase;
import br.com.foodhub.infra.web.api.user.UserApi;
import br.com.foodhub.infra.web.mapper.user.UserWebMapper;
import br.com.foodhub.infra.web.payload.pagination.PageResponsePayload;
import br.com.foodhub.infra.web.payload.user.UpdateUserPayload;
import br.com.foodhub.infra.web.payload.user.UserRequestPayload;
import br.com.foodhub.infra.web.payload.user.UserResponsePayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final ListUserUseCase listUserUseCase;
    private final UserWebMapper mapper;

    @PostMapping
    public UserResponsePayload create(@Valid @RequestBody UserRequestPayload payload) {
        var result = createUserUseCase.execute(mapper.toCreateDto(payload));

        return mapper.toResponse(result);
    }

    @GetMapping
    public PageResponsePayload<UserResponsePayload> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var result = listUserUseCase.execute(
                new PageRequestDTO(page, size)
        );

        return new PageResponsePayload<>(
                result.content().stream().map(mapper::toResponse).toList(),
                result.page(),
                result.size(),
                result.totalElements(),
                result.totalPages()
        );
    }


    @PatchMapping("/{userId}")
    public UserResponsePayload update(@PathVariable String userId, @Valid @RequestBody UpdateUserPayload payload) {
        var result = updateUserUseCase.execute(
                mapper.toUpdateDto(payload),
                userId
        );

        return mapper.toResponse(result);
    }
}
