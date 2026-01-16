package br.com.foodhub.infra.web.api.user;

import br.com.foodhub.infra.web.payload.address.UpdateUserAddressRequestPayload;
import br.com.foodhub.infra.web.payload.address.UserAddressRequestPayload;
import br.com.foodhub.infra.web.payload.address.UserAddressResponsePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Enderço usuário", description = "Operações de endereço de usuários.")
@RequestMapping("api/v1/users")
public interface UserAddressApi {

    @Operation(
            summary = "Listar endereços do usuário",
            description = "Retorna todos os endereços vinculados ao usuário"
    )
    @ApiResponse(responseCode = "200", description = "Lista de endereços")
    List<UserAddressResponsePayload> listAllAddress(
            @Parameter(description = "ID do usuário")
            @PathVariable String userId
    );

    @Operation(
            summary = "Criar endereço do usuário"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Endereço criado",
                    content = @Content(
                            schema = @Schema(implementation = UserAddressResponsePayload.class)
                    )
            )
    })
    UserAddressResponsePayload create(
            @Parameter(description = "ID do usuário")
            @PathVariable String userId,
            @RequestBody UserAddressRequestPayload payload
    );

    @Operation(
            summary = "Atualizar endereço do usuário"
    )
    UserAddressResponsePayload update(
            @Parameter(description = "ID do usuário")
            @PathVariable String userId,

            @Parameter(description = "ID do endereço")
            @PathVariable String userAddressId,

            @RequestBody UpdateUserAddressRequestPayload payload
    );

    @Operation(
            summary = "Remover endereço do usuário"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            @ApiResponse(responseCode = "204", description = "Endereço removido"),
            @ApiResponse(responseCode = "400", description = "Endereço não pertence ao usuário"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    void delete(
            @Parameter(description = "ID do usuário")
            @PathVariable String userId,

            @Parameter(description = "ID do endereço")
            @PathVariable String userAddressId
    );
}
