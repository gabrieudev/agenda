package br.com.gabrieudev.agenda.infrastructrure.web.dtos.status;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStatusDTO {
    @Schema(
        description = "Nome do status",
        example = "Pendente",
        required = true
    )
    @NotBlank(message = "Nome obrigatório")
    private String name;

    @Schema(
        description = "Descrição do status",
        example = "Status pendente",
        required = false
    )
    private String description;

    public Status toDomainObj() {
        return new ModelMapper().map(this, Status.class);
    }
}
