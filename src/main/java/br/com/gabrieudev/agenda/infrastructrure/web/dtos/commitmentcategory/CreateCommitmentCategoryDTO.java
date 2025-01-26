package br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitmentcategory;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.CommitmentCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommitmentCategoryDTO {
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
