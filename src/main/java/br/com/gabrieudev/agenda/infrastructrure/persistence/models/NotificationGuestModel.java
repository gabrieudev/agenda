package br.com.gabrieudev.agenda.infrastructrure.persistence.models;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.NotificationGuest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "notification_guests")
public class NotificationGuestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusModel status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private NotificationModel notification;

    public static NotificationGuestModel from(NotificationGuest notificationGuest) {
        return new ModelMapper().map(notificationGuest, NotificationGuestModel.class);
    }

    public NotificationGuest toDomainObj() {
        return new ModelMapper().map(this, NotificationGuest.class);
    }
}
