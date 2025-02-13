package br.com.gabrieudev.agenda.application.usecases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.application.gateways.NotificationGateway;
import br.com.gabrieudev.agenda.domain.entities.Notification;

public class NotificationInteractor {
    private final NotificationGateway notificationGateway;

    public NotificationInteractor(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    public Notification create(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        notification.setIsSended(Boolean.FALSE);

        return notificationGateway.create(notification);
    }

    public Notification update(Notification notification) {
        notification.setUpdatedAt(LocalDateTime.now());

        return notificationGateway.update(notification);
    }

    public Notification findById(UUID id) {
        return notificationGateway.findById(id);
    }

    public void deleteById(UUID id) {
        notificationGateway.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return notificationGateway.existsById(id);
    }

    public List<Notification> findByCommitmentId(UUID commitmentId, Integer page, Integer size) {
        return notificationGateway.findByCommitmentId(commitmentId, page, size);
    }

    public List<Notification> findByDueDateBeforeAndIsSendedFalse(LocalDateTime dueDate) {
        return notificationGateway.findByDueDateBeforeAndIsSendedFalse(dueDate);
    }
}
