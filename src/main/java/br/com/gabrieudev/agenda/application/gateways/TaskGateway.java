package br.com.gabrieudev.agenda.application.gateways;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.domain.entities.Task;

public interface TaskGateway {
    Task create(Task task);
    Task update(Task task);
    Task findById(UUID id);
    boolean existsById(UUID id);
    boolean existsByStatusId(UUID statusId);
    void deleteById(UUID id);
    List<Task> findByCommitmentId(UUID commitmentId, UUID statusId, String param, Integer page, Integer size);
    List<Task> findByUserIdAndStatusIdAndUpdatedBetween(UUID userId, UUID statusId, LocalDateTime startDate, LocalDateTime endDate);
}
