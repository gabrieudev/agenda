package br.com.gabrieudev.agenda.infrastructrure.gateways;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.agenda.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.agenda.application.exceptions.InvalidTokenException;
import br.com.gabrieudev.agenda.application.gateways.NotificationGateway;
import br.com.gabrieudev.agenda.domain.entities.Notification;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.NotificationModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.UserModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.NotificationRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.UserRepository;

@Service
public class NotificationServiceGateway implements NotificationGateway {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final JwtDecoder jwtDecoder;

    public NotificationServiceGateway(NotificationRepository notificationRepository, UserRepository userRepository, JwtDecoder jwtDecoder) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    @CacheEvict(value = "Notifications", key = "#notification.id")
    @Transactional
    public Notification create(Notification notification, String token) {
        try {
            var jwt = jwtDecoder.decode(token);
            String userId = jwt.getSubject();

            UserModel user = userRepository.findById(UUID.fromString(userId))
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

            notification.setUser(user.toDomainObj());

            return notificationRepository.save(NotificationModel.from(notification)).toDomainObj();
        } catch (Exception e) {
            throw new InvalidTokenException("Token inválido");
        }
    }

    @Override
    @CacheEvict(value = "Notifications", key = "#id")
    @Transactional
    public void deleteById(UUID id) {
        if (!existsById(id)) {
            throw new EntityNotFoundException("Notificação não encontrada");
        }

        notificationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return notificationRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> findByDueDateBeforeAndIsSendedFalse(LocalDateTime dueDate) {
        return notificationRepository.findByDueDateBeforeAndIsSendedFalse(dueDate)
                .stream()
                .map(NotificationModel::toDomainObj)
                .toList();
    }

    @Override
    @Cacheable(value = "Notifications", key = "#id")
    @Transactional(readOnly = true)
    public Notification findById(UUID id) {
        return notificationRepository.findById(id)
                .map(NotificationModel::toDomainObj)
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> findByUserId(UUID userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return notificationRepository.findByUser(user, pageable)
                .stream()
                .map(NotificationModel::toDomainObj)
                .toList();
    }

    @Override
    @CacheEvict(value = "Notifications", key = "#notification.id")
    @Transactional
    public Notification update(Notification notification) {
        if (!existsById(notification.getId())) {
            throw new EntityNotFoundException("Notificação não encontrada");
        }

        NotificationModel notificationToUpdate = notificationRepository.findById(notification.getId())
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada"));

        notificationToUpdate.update(notification);

        return notificationRepository.save(notificationToUpdate).toDomainObj();
    }
}
