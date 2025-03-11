package br.com.gabrieudev.agenda.infrastructrure.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.agenda.infrastructrure.persistence.models.NotificationGuestModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.NotificationModel;

@Repository
public interface NotificationGuestRepository extends JpaRepository<NotificationGuestModel, UUID> {
    @Query(
        value = """
                SELECT n.*
                FROM notification_guests n
                LEFT JOIN statuses s 
                    ON n.status_id = s.id
                WHERE (:p1 IS NULL OR n.user_id = :p1)
                AND (:p2 IS NULL OR s.id = :p2)
                AND (:p3 IS NULL OR n.notification_id = :p3)
                """,
        nativeQuery = true
    )
    Page<NotificationGuestModel> findAllByCriteria(
        @Param("p1") UUID userId,
        @Param("p2") UUID statusId,
        @Param("p3") UUID notificationId,
        Pageable pageable
    );

    List<NotificationGuestModel> findByNotification(NotificationModel notification);
}
