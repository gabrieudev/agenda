package br.com.gabrieudev.agenda.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.domain.entities.NotificationGuest;

public interface NotificationGuestGateway {
    NotificationGuest create(NotificationGuest notificationGuest);
    NotificationGuest findById(UUID id);
    void deleteById(UUID id);
    boolean existsById(UUID id);
    List<NotificationGuest> findAllByCriteria(UUID userId, UUID statusId, UUID notificationId, Integer page, Integer size);
    NotificationGuest update(NotificationGuest notificationGuest);
}
