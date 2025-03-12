package br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitment;

import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Commitment;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitmentcategory.CommitmentCategoryDTO;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.status.StatusDTO;
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
public class UpdateCommitmentDTO {
    @Schema(
        description = "Identificador do compromisso",
        example = "123e4567-e89b-12d3-a456-426614174000",
        required = true
    )
    @NotNull(message = "Identificador obrigatório")
    private UUID id;

    @Schema(
        description = "Titulo do compromisso",
        example = "Reuniao de trabalho",
        required = true
    )
    @NotBlank(message = "Titulo obrigatório")
    private String title;

    @Schema(
        description = "Descrição do compromisso",
        example = "Reuniao de trabalho",
        required = false
    )
    private String description;
    
    @NotNull(message = "Categoria obrigatória")
    private CommitmentCategoryDTO category;

    private StatusDTO status;
    
    @Schema(
        description = "Data de vencimento do compromisso",
        example = "2023-01-01T00:00:00",
        required = true
    )
    @NotNull(message = "Data de vencimento obrigatória")
    @Future(message = "Data de vencimento deve ser futura")
    private LocalDateTime dueDate;

    public Commitment toDomainObj() {
        return new ModelMapper().map(this, Commitment.class);
    }
}
