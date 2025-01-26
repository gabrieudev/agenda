package br.com.gabrieudev.agenda.application.gateways;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.domain.entities.Commitment;

public interface CommitmentGateway {
    Commitment create(Commitment commitment, String token);
    Commitment update(Commitment commitment);
    Commitment findById(UUID id);
    boolean existsById(UUID id);
    boolean existsByCommitmentCategoryId(UUID commitmentCategoryId);
    boolean existsByStatusId(UUID statusId);
    void deleteById(UUID id);
    List<Commitment> findByUserId(UUID userId, UUID commitmentCategoryId, UUID statusId, String param, Integer page, Integer size);
    List<Commitment> findByDueDateBeforeAndStatusId(LocalDateTime dueDate, UUID statusId);
    List<Commitment> findByUserIdAndStatusIdAndUpdatedBetween(UUID userId, UUID statusId, LocalDateTime startDate, LocalDateTime endDate);
}
