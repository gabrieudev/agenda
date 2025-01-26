package br.com.gabrieudev.agenda.infrastructrure.web.dtos.report;

import java.util.List;

import br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitment.CommitmentDTO;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.task.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private List<CommitmentDTO> completedCommitments;

    private List<TaskDTO> completedTasks;
}
