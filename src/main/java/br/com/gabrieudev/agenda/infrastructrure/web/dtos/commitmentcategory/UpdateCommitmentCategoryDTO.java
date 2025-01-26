package br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitmentcategory;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.CommitmentCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommitmentCategoryDTO {
    @Schema(
        description = "Identificador da categoria",
        example = "123e4567-e89b-12d3-a456-426614174000",
        required = true
    )
    @NotNull(message = "Identificador obrigatório")
    private UUID id;

    @Schema(
        description = "Nome da categoria",
        example = "Trabalho",
        required = true
    )
    @NotBlank(message = "Nome obrigatório")
    private String name;

    @Schema(
        description = "Descrição da categoria",
        example = "Categoria de trabalho",
        required = false
    )
    private String description;

    public CommitmentCategory toDomainObj() {
        return new ModelMapper().map(this, CommitmentCategory.class);
    }
}
