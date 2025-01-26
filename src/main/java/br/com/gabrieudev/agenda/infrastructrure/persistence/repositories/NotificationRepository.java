package br.com.gabrieudev.agenda.infrastructrure.persistence.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.agenda.infrastructrure.persistence.models.NotificationModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.UserModel;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationModel, UUID> {
    Page<NotificationModel> findByUser(UserModel user, Pageable pageable);
    List<NotificationModel> findByDueDateBeforeAndIsSendedFalse(LocalDateTime dueDate);
}
