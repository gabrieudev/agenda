package br.com.gabrieudev.agenda.infrastructrure.gateways;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.agenda.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.agenda.application.gateways.NotificationGuestGateway;
import br.com.gabrieudev.agenda.domain.entities.NotificationGuest;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.NotificationGuestModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.NotificationGuestRepository;

public class NotificationGuestServiceGateway implements NotificationGuestGateway {
    private final NotificationGuestRepository notificationGuestRepository;

    public NotificationGuestServiceGateway(NotificationGuestRepository notificationGuestRepository) {
        this.notificationGuestRepository = notificationGuestRepository;
    }

    @Override
    @CacheEvict(value = "NotificationGuests", key = "#notificationGuest.id")
    @Transactional
    public NotificationGuest create(NotificationGuest notificationGuest) {
        return notificationGuestRepository.save(NotificationGuestModel.from(notificationGuest)).toDomainObj();
    }

    @Override
    @CacheEvict(value = "NotificationGuests", key = "#id")
    @Transactional
    public void deleteById(UUID id) {
        if (!notificationGuestRepository.existsById(id)) {
            throw new EntityNotFoundException("Notificação não encontrada");
        }

        notificationGuestRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return notificationGuestRepository.existsById(id);
    }

    @Override
    @Cacheable(value = "NotificationGuests", key = "#id")
    @Transactional(readOnly = true)
    public NotificationGuest findById(UUID id) {
        return notificationGuestRepository.findById(id)
            .map(NotificationGuestModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationGuest> findByUserId(UUID userId, UUID statusId, Integer page, Integer size) {
        return notificationGuestRepository.findByUserId(userId, statusId, PageRequest.of(page, size))
            .stream()
            .map(NotificationGuestModel::toDomainObj)
            .toList();
    }
}
