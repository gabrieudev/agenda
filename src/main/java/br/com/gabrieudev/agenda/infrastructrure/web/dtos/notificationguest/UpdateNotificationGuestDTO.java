package br.com.gabrieudev.agenda.infrastructrure.web.dtos.notificationguest;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.NotificationGuest;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.status.StatusDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNotificationGuestDTO {
    @NotNull(message = "Status obrigatório")
    private StatusDTO status;

    public NotificationGuest toDomainObj() {
        return new ModelMapper().map(this, NotificationGuest.class);
    }
}
