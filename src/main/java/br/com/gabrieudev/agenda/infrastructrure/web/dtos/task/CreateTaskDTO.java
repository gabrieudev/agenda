package br.com.gabrieudev.agenda.infrastructrure.web.dtos.task;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Task;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitment.CommitmentDTO;
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
public class CreateTaskDTO {
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
    
    @NotNull(message = "Compromisso obrigatório")
    private CommitmentDTO commitment;

    @NotNull(message = "Status obrigatório")
    private StatusDTO status;

    public Task toDomainObj() {
        return new ModelMapper().map(this, Task.class);
    }
}
