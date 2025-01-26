package br.com.gabrieudev.agenda.infrastructrure.web.dtos.notification;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationDTO {
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
    private LocalDateTime dueDate;

    public Notification toDomainObj() {
        return new ModelMapper().map(this, Notification.class);
    }
}
