package br.com.gabrieudev.agenda.application.gateways;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.domain.entities.Notification;

public interface NotificationGateway {
    Notification create(Notification notification);
    Notification update(Notification notification);
    Notification findById(UUID id);
    List<Notification> findByCommitmentId(UUID commitmentId, Integer page, Integer size);
    List<Notification> findByDueDateBeforeAndIsSendedFalse(LocalDateTime dueDate);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
