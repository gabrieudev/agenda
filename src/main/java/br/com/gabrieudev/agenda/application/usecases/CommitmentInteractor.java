package br.com.gabrieudev.agenda.application.usecases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.application.gateways.CommitmentGateway;
import br.com.gabrieudev.agenda.application.gateways.StatusGateway;
import br.com.gabrieudev.agenda.application.gateways.TaskGateway;
import br.com.gabrieudev.agenda.domain.entities.Commitment;
import br.com.gabrieudev.agenda.domain.entities.Status;
import br.com.gabrieudev.agenda.domain.entities.Task;

public class CommitmentInteractor {
    private final CommitmentGateway commitmentGateway;
    private final StatusGateway statusGateway;
    private final TaskGateway taskGateway;

    public CommitmentInteractor(CommitmentGateway commitmentGateway, StatusGateway statusGateway, TaskGateway taskGateway) {
        this.commitmentGateway = commitmentGateway;
        this.statusGateway = statusGateway;
        this.taskGateway = taskGateway;
    }

    public Commitment create(Commitment commitment, String token) {
        Status status = statusGateway.findByName("Em andamento");

        commitment.setStatus(status);
        commitment.setCreatedAt(LocalDateTime.now());
        commitment.setUpdatedAt(LocalDateTime.now());

        return commitmentGateway.create(commitment, token);
    }

    public Commitment update(Commitment commitment) {
        commitment.setUpdatedAt(LocalDateTime.now());

        return commitmentGateway.update(commitment);
    }

    public Commitment findById(UUID id) {
        return commitmentGateway.findById(id);
    }

    public void deleteById(UUID id) {
        List<Task> tasks = taskGateway.findByCommitmentId(id, null, null);

        tasks.forEach(task -> taskGateway.deleteById(task.getId()));

        commitmentGateway.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return commitmentGateway.existsById(id);
    }

    public boolean existsByCommitmentCategoryId(UUID commitmentCategoryId) {
        return commitmentGateway.existsByCommitmentCategoryId(commitmentCategoryId);
    }

    public boolean existsByStatusId(UUID statusId) {
        return commitmentGateway.existsByStatusId(statusId);
    }

    public List<Commitment> findByUserId(UUID userId, UUID commitmentCategoryId, UUID statusId, String param, Integer page, Integer size) {
        return commitmentGateway.findByUserId(userId, commitmentCategoryId, statusId, param, page, size);
    }

    public List<Commitment> findByDueDateBeforeAndStatusId(LocalDateTime dueDate, UUID statusId) {
        return commitmentGateway.findByDueDateBeforeAndStatusId(dueDate, statusId);
    }

    public List<Commitment> findByUserIdAndStatusIdAndUpdatedBetween(UUID userId, UUID statusId, LocalDateTime startDate, LocalDateTime endDate) {
        return commitmentGateway.findByUserIdAndStatusIdAndUpdatedBetween(userId, statusId, startDate, endDate);
    }
}
