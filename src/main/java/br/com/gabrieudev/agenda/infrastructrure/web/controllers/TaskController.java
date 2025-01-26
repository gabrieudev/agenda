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
import br.com.gabrieudev.agenda.application.usecases.TaskInteractor;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.task.CreateTaskDTO;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.task.TaskDTO;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.task.UpdateTaskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/tasks")
public class TaskController {
    private final TaskInteractor taskInteractor;

    public TaskController(TaskInteractor taskInteractor) {
        this.taskInteractor = taskInteractor;
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Criar tarefa",
        description = "Cria uma tarefa de acordo com o corpo da requisição",
        tags = "Tasks",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Tarefa criada"
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
    public ResponseEntity<TaskDTO> create(
        @Valid
        @RequestBody
        CreateTaskDTO createTaskDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(TaskDTO.from(taskInteractor.create(createTaskDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Atualizar tarefa",
        description = "Atualiza uma tarefa de acordo com o corpo da requisição",
        tags = "Tasks",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Tarefa atualizada"
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
                description = "Tarefa não encontrada",
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
    public ResponseEntity<TaskDTO> update(
        @Valid
        @RequestBody
        UpdateTaskDTO updateTaskDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(TaskDTO.from(taskInteractor.update(updateTaskDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter tarefa",
        description = "Obtém uma tarefa de acordo com o ID",
        tags = "Tasks",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Tarefa obtida"
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
                description = "Tarefa não encontrada",
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
    public ResponseEntity<TaskDTO> findById(
        @Parameter(
            description = "Identificador da tarefa",
            example = "123e4567-e89b-12d3-a456-426614174000"
        )
        @PathVariable UUID id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(TaskDTO.from(taskInteractor.findById(id)));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter tarefas",
        description = "Obtém todas as tarefas de acordo com os parâmetros da requisição",
        tags = "Tasks",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Tarefas obtidas"
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
    public ResponseEntity<Page<TaskDTO>> findByCommitmentId(
        @Parameter(
            name = "commitmentId",
            description = "Identificador do compromisso"
        )
        @RequestParam(required = true) UUID commitmentId,
        
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
        List<TaskDTO> tasks = taskInteractor.findByCommitmentId(commitmentId, statusId, param, page, size)
            .stream()
            .map(TaskDTO::from)
            .toList();

        Page<TaskDTO> tasksPage = new PageImpl<>(tasks, PageRequest.of(page, size), tasks.size());

        return ResponseEntity.status(HttpStatus.OK).body(tasksPage);
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Deletar tarefa",
        description = "Deleta uma tarefa de acordo com o ID",
        tags = "Tasks",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Tarefa deletada"
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
                description = "Tarefa não encontrada",
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
            description = "Identificador da tarefa",
            example = "123e4567-e89b-12d3-a456-426614174000"
        )
        @PathVariable UUID id
    ) {
        taskInteractor.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
