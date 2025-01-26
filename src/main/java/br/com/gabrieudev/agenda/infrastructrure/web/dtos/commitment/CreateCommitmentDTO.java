package br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitment;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Commitment;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitmentcategory.CommitmentCategoryDTO;
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
public class CreateCommitmentDTO {
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
