package br.com.gabrieudev.agenda.infrastructrure.web.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.agenda.application.exceptions.StandardException;
import br.com.gabrieudev.agenda.application.usecases.CommitmentInteractor;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitment.CommitmentDTO;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitment.CreateCommitmentDTO;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitment.UpdateCommitmentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/commitments")
public class CommitmentController {
    private final CommitmentInteractor commitmentInteractor;

    public CommitmentController(CommitmentInteractor commitmentInteractor) {
        this.commitmentInteractor = commitmentInteractor;
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Criar compromisso",
        description = "Cria um compromisso de acordo com o corpo da requisição",
        tags = "Commitments",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Compromisso cadastrado"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "406",
                description = "Informações inválidas no corpo da requisição",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @PostMapping
    public ResponseEntity<CommitmentDTO> create(
        @Valid
        @RequestBody
        CreateCommitmentDTO createCommitmentDTO,

        HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization").split(" ")[1];

        return ResponseEntity.status(HttpStatus.CREATED).body(CommitmentDTO.from(commitmentInteractor.create(createCommitmentDTO.toDomainObj(), token)));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Atualizar compromisso",
        description = "Atualiza um compromisso de acordo com o corpo da requisição",
        tags = "Commitments",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Compromisso atualizado"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Compromisso não encontrado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "406",
                description = "Informações inválidas no corpo da requisição",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @PutMapping
    public ResponseEntity<CommitmentDTO> update(
        @Valid 
        @RequestBody 
        UpdateCommitmentDTO updateCommitmentDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CommitmentDTO.from(commitmentInteractor.update(updateCommitmentDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter compromisso",
        description = "Obtém um compromisso de acordo com o ID",
        tags = "Commitments",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Compromisso obtido"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Compromisso não encontrado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CommitmentDTO> findById(
        @Parameter(
            description = "Identificador do compromisso",
            example = "123e4567-e89b-12d3-a456-426614174000"
        )
        @PathVariable UUID id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CommitmentDTO.from(commitmentInteractor.findById(id)));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter compromissos",
        description = "Obtém todos os compromissos de acordo com os parâmetros da requisição",
        tags = "Commitments",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Compromissos obtidos"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @GetMapping
    public ResponseEntity<Page<CommitmentDTO>> findAll(
        @Parameter(
            name = "userId",
            description = "Identificador do usuário"
        )
        @RequestParam(required = true) UUID userId,

        @Parameter(
            name = "commitmentCategoryId",
            description = "Identificador da categoria"
        )
        @RequestParam(required = false) UUID commitmentCategoryId,

        @Parameter(
            name = "statusId",
            description = "Identificador do status"
        )
        @RequestParam(required = false) UUID statusId,

        @Parameter(
            name = "page",
            description = "Página atual da paginação"
        )
        @RequestParam(required = true) Integer page,
        
        @Parameter(
            name = "size",
            description = "Quantidade de itens por página"
        )
        @RequestParam(required = true) Integer size,
        
        @Parameter(
            name = "param",
            description = "Parâmetro de busca"
        )
        @RequestParam(required = false) String param
    ) {
        List<CommitmentDTO> commitments = commitmentInteractor.findByUserId(userId, commitmentCategoryId, statusId, param, page, size)
            .stream()
            .map(CommitmentDTO::from)
            .toList();

        Page<CommitmentDTO> commitmentsPage = new PageImpl<>(commitments, PageRequest.of(page, size), commitments.size());

        return ResponseEntity.status(HttpStatus.OK).body(commitmentsPage);
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Deletar compromisso",
        description = "Deleta um compromisso de acordo com o ID",
        tags = "Commitments",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Compromisso deletado"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Compromisso não encontrado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(
            description = "Identificador do compromisso",
            example = "123e4567-e89b-12d3-a456-426614174000"
        )
        @PathVariable UUID id
    ) {
        commitmentInteractor.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
