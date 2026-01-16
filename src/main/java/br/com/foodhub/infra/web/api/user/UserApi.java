package br.com.foodhub.infra.web.api.user;

import br.com.foodhub.infra.web.payload.pagination.PageResponsePayload;
import br.com.foodhub.infra.web.payload.user.UpdateUserPayload;
import br.com.foodhub.infra.web.payload.user.UserRequestPayload;
import br.com.foodhub.infra.web.payload.user.UserResponsePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Usuários", description = "Operações de consulta e registro de usuários.")
public interface UserApi {

    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário no sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = UserResponsePayload.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email, telefone ou CPF já cadastrado",
                    content = @Content
            )
    })
    UserResponsePayload create(
            @RequestBody UserRequestPayload payload
    );

    @Operation(
            summary = "Listar usuários",
            description = "Lista usuários com paginação"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários retornada com sucesso"
            )
    })
    PageResponsePayload<UserResponsePayload> list(
            @Parameter(description = "Número da página", example = "0")
            @RequestParam int page,

            @Parameter(description = "Quantidade de registros por página", example = "10")
            @RequestParam int size
    );

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza parcialmente os dados de um usuário"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = UserResponsePayload.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado"
            )
    })
    UserResponsePayload update(
            @Parameter(description = "ID do usuário")
            @PathVariable String userId,

            @RequestBody UpdateUserPayload payload
    );
}
