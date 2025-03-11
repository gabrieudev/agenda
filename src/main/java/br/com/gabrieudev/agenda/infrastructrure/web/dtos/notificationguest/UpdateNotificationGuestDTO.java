package br.com.gabrieudev.agenda.infrastructrure.web.dtos.notificationguest;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.NotificationGuest;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.status.StatusDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNotificationGuestDTO {
    @Schema(
        description = "Identificador do convite",
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    @NotNull(message = "Identificador obrigatório")
    private UUID id;

    @NotNull(message = "Status obrigatório")
    private StatusDTO status;

    public NotificationGuest toDomainObj() {
        return new ModelMapper().map(this, NotificationGuest.class);
    }
}
