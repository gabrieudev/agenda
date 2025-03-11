package br.com.gabrieudev.agenda.application.usecases;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.application.gateways.NotificationGuestGateway;
import br.com.gabrieudev.agenda.application.gateways.StatusGateway;
import br.com.gabrieudev.agenda.domain.entities.NotificationGuest;
import br.com.gabrieudev.agenda.domain.entities.Status;

public class NotificationGuestInteractor {
    private final NotificationGuestGateway notificationGuestGateway;
    private final StatusGateway statusGateway;

    public NotificationGuestInteractor(NotificationGuestGateway notificationGuestGateway, StatusGateway statusGateway) {
        this.notificationGuestGateway = notificationGuestGateway;
        this.statusGateway = statusGateway;
    }

    public NotificationGuest create(NotificationGuest notificationGuest) {
        Status pendingStatus = statusGateway.findByName("Pendente");

        notificationGuest.setStatus(pendingStatus);

        return notificationGuestGateway.create(notificationGuest);
    }

    public NotificationGuest findById(UUID id) {
        return notificationGuestGateway.findById(id);
    }
    
    public void deleteById(UUID id) {
        notificationGuestGateway.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return notificationGuestGateway.existsById(id);
    }

    public List<NotificationGuest> findAllByCriteria(UUID userId, UUID statusId, UUID notificationId, Integer page, Integer size) {
        return notificationGuestGateway.findAllByCriteria(userId, statusId, notificationId, page, size);
    }

    public NotificationGuest update(NotificationGuest notificationGuest) {
        return notificationGuestGateway.update(notificationGuest);
    }
}
