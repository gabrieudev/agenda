package br.com.gabrieudev.agenda.infrastructrure.web.dtos.notification;

import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Notification;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private UUID id;
    private String message;
    private UserDTO user;
    private Boolean isSended;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NotificationDTO from(Notification notification) {
        return new ModelMapper().map(notification, NotificationDTO.class);
    }

    public Notification toDomainObj() {
        return new ModelMapper().map(this, Notification.class);
    }
}
