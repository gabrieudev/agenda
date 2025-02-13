package br.com.gabrieudev.agenda.infrastructrure.web.dtos.notificationguest;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.NotificationGuest;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.notification.NotificationDTO;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.status.StatusDTO;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationGuestDTO {
    private UUID id;
    private StatusDTO status;
    private UserDTO user;
    private NotificationDTO notification;

    public static NotificationGuestDTO from(NotificationGuest notificationGuest) {
        return new ModelMapper().map(notificationGuest, NotificationGuestDTO.class);
    }

    public NotificationGuest toDomainObj() {
        return new ModelMapper().map(this, NotificationGuest.class);
    }
}
