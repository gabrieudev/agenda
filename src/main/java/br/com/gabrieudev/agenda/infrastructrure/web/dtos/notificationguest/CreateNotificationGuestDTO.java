package br.com.gabrieudev.agenda.infrastructrure.web.dtos.notificationguest;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.NotificationGuest;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.notification.NotificationDTO;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.user.UserDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationGuestDTO {
    @NotNull(message = "Usuário obrigatório")
    private UserDTO user;
    
    @NotNull(message = "Notificação obrigatória")
    private NotificationDTO notification;

    public NotificationGuest toDomainObj() {
        return new ModelMapper().map(this, NotificationGuest.class);
    }
}
