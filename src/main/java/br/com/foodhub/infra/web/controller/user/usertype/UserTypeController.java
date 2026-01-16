package br.com.foodhub.infra.web.controller.user.usertype;

import br.com.foodhub.core.application.dto.user.usertype.UserTypeRequestDTO;
import br.com.foodhub.core.application.dto.user.usertype.UserTypeResultDTO;
import br.com.foodhub.core.application.usecase.user.usertype.CreateUserTypeUseCase;
import br.com.foodhub.core.application.usecase.user.usertype.DeleteUserTypeUseCase;
import br.com.foodhub.core.application.usecase.user.usertype.ListUserTypeUseCase;
import br.com.foodhub.core.application.usecase.user.usertype.UpdateUserTypeUseCase;
import br.com.foodhub.infra.web.api.user.usertype.UserTypeApi;
import br.com.foodhub.infra.web.mapper.usertype.UserTypeWebMapper;
import br.com.foodhub.infra.web.payload.user.usertype.UserTypeRequestPayload;
import br.com.foodhub.infra.web.payload.user.usertype.UserTypeResponsePayload;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users")
@RestController
@RequestMapping("api/v1/user-types")
@RequiredArgsConstructor
public class UserTypeController implements UserTypeApi {

    private final CreateUserTypeUseCase createUserTypeUseCase;
    private final UpdateUserTypeUseCase updateUserTypeUseCase;
    private final DeleteUserTypeUseCase deleteUserTypeUseCase;
    private final ListUserTypeUseCase listUserTypeUseCase;

    private final UserTypeWebMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserTypeResponsePayload create(
            @RequestBody @Valid UserTypeRequestPayload payload
    ) {
        UserTypeResultDTO result =
                createUserTypeUseCase.execute(mapper.toCreateDto(payload));
        return mapper.toResponse(result);
    }

    @PutMapping("/{userTypeId}")
    public UserTypeResponsePayload update(
            @PathVariable String userTypeId,
            @RequestBody @Valid UserTypeRequestPayload payload
    ) {
        UserTypeRequestDTO dto = mapper.toCreateDto(payload);
        UserTypeResultDTO result =
                updateUserTypeUseCase.execute(userTypeId, dto);
        return mapper.toResponse(result);
    }

    @GetMapping
    public List<UserTypeResponsePayload> list() {
        return listUserTypeUseCase.execute()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @DeleteMapping("/{userTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String userTypeId) {
        deleteUserTypeUseCase.execute(userTypeId);
    }
}
