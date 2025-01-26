package br.com.gabrieudev.agenda.infrastructrure.web.dtos.status;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {
    private UUID id;
    private String name;
    private String description;

    public static StatusDTO from(Status status) {
        return new ModelMapper().map(status, StatusDTO.class);
    }

    public Status toDomainObj() {
        return new ModelMapper().map(this, Status.class);
    }
}
