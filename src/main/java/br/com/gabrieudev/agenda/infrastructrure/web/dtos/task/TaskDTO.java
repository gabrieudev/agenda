package br.com.gabrieudev.agenda.infrastructrure.web.dtos.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Task;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitment.CommitmentDTO;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.status.StatusDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private UUID id;
    private String title;
    private String description;
    private CommitmentDTO commitment;
    private StatusDTO status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TaskDTO from(Task task) {
        return new ModelMapper().map(task, TaskDTO.class);
    }

    public Task toDomainObj() {
        return new ModelMapper().map(this, Task.class);
    }
}
