package br.com.gabrieudev.agenda.application.usecases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.application.gateways.StatusGateway;
import br.com.gabrieudev.agenda.application.gateways.TaskGateway;
import br.com.gabrieudev.agenda.domain.entities.Status;
import br.com.gabrieudev.agenda.domain.entities.Task;

public class TaskInteractor {
    private final TaskGateway taskGateway;
    private final StatusGateway statusGateway;

    public TaskInteractor(TaskGateway taskGateway, StatusGateway statusGateway) {
        this.taskGateway = taskGateway;
        this.statusGateway = statusGateway;
    }

    public Task create(Task task) {
        Status status = statusGateway.findByName("Em andamento");

        task.setStatus(status);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        
        return taskGateway.create(task);
    }

    public Task update(Task task) {
        task.setUpdatedAt(LocalDateTime.now());

        return taskGateway.update(task);
    }

    public Task findById(UUID id) {
        return taskGateway.findById(id);
    }

    public void deleteById(UUID id) {
        taskGateway.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return taskGateway.existsById(id);
    }

    public boolean existsByStatusId(UUID statusId) {
        return taskGateway.existsByStatusId(statusId);
    }

    public List<Task> findByCommitmentId(UUID commitmentId, UUID statusId, String param, Integer page, Integer size) {
        return taskGateway.findByCommitmentId(commitmentId, statusId, param, page, size);
    }

    public List<Task> findByUserIdAndStatusIdAndUpdatedBetween(UUID userId, UUID statusId, LocalDateTime startDate, LocalDateTime endDate) {
        return taskGateway.findByUserIdAndStatusIdAndUpdatedBetween(userId, statusId, startDate, endDate);
    }
}
