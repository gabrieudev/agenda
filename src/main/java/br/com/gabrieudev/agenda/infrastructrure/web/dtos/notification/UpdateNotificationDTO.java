package br.com.gabrieudev.agenda.infrastructrure.web.dtos.notification;

import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNotificationDTO {
    @Schema(
        description = "Identificador da notificação",
        example = "a8b5c4d6-7a8b-4c5d-6e7f-8a9b0c1d2e3f",
        required = true
    )
    @NotNull(message = "Identificador obrigatório")
    private UUID id;

    @Schema(
        description = "Mensagem de notificação",
        example = "Aviso de compromisso",
        required = true
    )
    @NotBlank(message = "Mensagem obrigatória")
    private String message;
    
    @Schema(
        description = "Data de vencimento da notificação",
        example = "2023-01-01T00:00:00",
        required = true
    )
    @NotNull(message = "Data de vencimento obrigatória")
    @Future(message = "Data de vencimento deve ser futura")
    private LocalDateTime dueDate;

    public Notification toDomainObj() {
        return new ModelMapper().map(this, Notification.class);
    }
}
