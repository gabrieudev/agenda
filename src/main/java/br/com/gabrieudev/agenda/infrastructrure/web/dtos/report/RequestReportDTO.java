package br.com.gabrieudev.agenda.infrastructrure.web.dtos.report;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestReportDTO {
    @Schema(
        description = "Identificador do usuário",
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    @NotNull(message = "Identificador do usuário obrigatório")
    private UUID userId;

    @Schema(
        description = "Data inicial do relatório",
        example = "2023-01-01T00:00:00"
    )
    @NotNull(message = "Data inicial obrigatória")
    @Past(message = "Data inicial deve estar no passado")
    private LocalDateTime startDate;

    @Schema(
        description = "Data final do relatório",
        example = "2023-01-01T00:00:00"
    )
    @NotNull(message = "Data final obrigatória")
    @Past(message = "Data final deve estar no passado")
    private LocalDateTime endDate;
}
