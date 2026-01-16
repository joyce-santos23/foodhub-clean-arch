package br.com.foodhub.infra.web.api.user.usertype;

import br.com.foodhub.infra.web.payload.user.usertype.UserTypeRequestPayload;
import br.com.foodhub.infra.web.payload.user.usertype.UserTypeResponsePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserTypeApi {

    @Operation(
            summary = "Criar tipo de usuário",
            description = "Cria um novo tipo de usuário no sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo de usuário criado"),
            @ApiResponse(responseCode = "409", description = "Tipo de usuário inválido ou duplicado")
    })
    UserTypeResponsePayload create(
            @RequestBody UserTypeRequestPayload payload
    );

    @Operation(
            summary = "Atualizar tipo de usuário",
            description = "Atualiza o nome de um tipo de usuário existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de usuário atualizado"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    UserTypeResponsePayload update(
            @Parameter(description = "ID do tipo de usuário")
            @PathVariable String userTypeId,

            @RequestBody UserTypeRequestPayload payload
    );

    @Operation(
            summary = "Listar tipos de usuário",
            description = "Retorna todos os tipos de usuário cadastrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista de tipos de usuário")
    List<UserTypeResponsePayload> list();

    @Operation(
            summary = "Remover tipo de usuário",
            description = "Remove um tipo de usuário do sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tipo de usuário removido"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Tipo de usuário é do sistema ou está em uso")
    })
    void delete(
            @Parameter(description = "ID do tipo de usuário")
            @PathVariable String userTypeId
    );
}
