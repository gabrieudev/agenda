package br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitment;

import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Commitment;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitmentcategory.CommitmentCategoryDTO;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.status.StatusDTO;
import br.com.gabrieudev.agenda.infrastructrure.web.dtos.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommitmentDTO {
    private UUID id;
    private String title;
    private String description;
    private CommitmentCategoryDTO category;
    private StatusDTO status;
    private UserDTO user;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommitmentDTO from(Commitment commitment) {
        return new ModelMapper().map(commitment, CommitmentDTO.class);
    }

    public Commitment toDomainObj() {
        return new ModelMapper().map(this, Commitment.class);
    }
}
