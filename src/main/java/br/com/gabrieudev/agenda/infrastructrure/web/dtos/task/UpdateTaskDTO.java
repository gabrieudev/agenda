package br.com.gabrieudev.agenda.infrastructrure.web.dtos.task;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Task;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.status.StatusDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskDTO {
    @Schema(
        description = "Identificador da tarefa",
        example = "f4f1d7f5-9d3c-4b1b-8b1b-1b1b1b1b1b1b",
        required = true
    )
    @NotNull(message = "Identificador obrigatório")
    private UUID id;
    
    @Schema(
        description = "Titulo da tarefa",
        example = "Reuniao de trabalho",
        required = true
    )
    @NotBlank(message = "Titulo obrigatório")
    private String title;
    
    @Schema(
        description = "Descrição da tarefa",
        example = "Reuniao de trabalho",
        required = false
    )
    private String description;
    
    @NotNull(message = "Status obrigatório")
    private StatusDTO status;

    public Task toDomainObj() {
        return new ModelMapper().map(this, Task.class);
    }
}
