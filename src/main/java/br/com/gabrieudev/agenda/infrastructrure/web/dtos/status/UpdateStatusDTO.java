package br.com.gabrieudev.agenda.infrastructrure.web.dtos.status;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusDTO {
    @Schema(
        description = "Identificador do status",
        example = "123e4567-e89b-12d3-a456-426655440000",
        required = true
    )
    @NotNull(message = "Identificador obrigatório")
    private UUID id;

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
