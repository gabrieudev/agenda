package br.com.gabrieudev.agenda.application.usecases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.application.gateways.CommitmentGateway;
import br.com.gabrieudev.agenda.application.gateways.NotificationGateway;
import br.com.gabrieudev.agenda.application.gateways.NotificationGuestGateway;
import br.com.gabrieudev.agenda.application.gateways.StatusGateway;
import br.com.gabrieudev.agenda.application.gateways.TaskGateway;
import br.com.gabrieudev.agenda.domain.entities.Commitment;
import br.com.gabrieudev.agenda.domain.entities.Notification;
import br.com.gabrieudev.agenda.domain.entities.NotificationGuest;
import br.com.gabrieudev.agenda.domain.entities.Status;
import br.com.gabrieudev.agenda.domain.entities.Task;

public class CommitmentInteractor {
    private final CommitmentGateway commitmentGateway;
    private final StatusGateway statusGateway;
    private final TaskGateway taskGateway;
    private final NotificationGuestGateway notificationGuestGateway;
    private final NotificationGateway notificationGateway;

    
    public CommitmentInteractor(CommitmentGateway commitmentGateway, StatusGateway statusGateway, TaskGateway taskGateway, NotificationGuestGateway notificationGuestGateway, NotificationGateway notificationGateway) {
        this.commitmentGateway = commitmentGateway;
        this.statusGateway = statusGateway;
        this.taskGateway = taskGateway;
        this.notificationGuestGateway = notificationGuestGateway;
        this.notificationGateway = notificationGateway;
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
        
        List<Notification> notifications = notificationGateway.findByCommitmentId(id, 0, Integer.MAX_VALUE);

        notifications.forEach(notification -> {
            List<NotificationGuest> guests = notificationGuestGateway.findAllByCriteria(null, null, notification.getId(), 0, Integer.MAX_VALUE);
            
            guests.forEach(guest -> notificationGuestGateway.deleteById(guest.getId()));

            notificationGateway.deleteById(notification.getId());
        });
        
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
