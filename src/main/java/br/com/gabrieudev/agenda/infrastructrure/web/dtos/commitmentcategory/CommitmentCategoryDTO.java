package br.com.gabrieudev.agenda.infrastructrure.web.dtos.commitmentcategory;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.CommitmentCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommitmentCategoryDTO {
    private UUID id;
    private String name;
    private String description;

    public static CommitmentCategoryDTO from(CommitmentCategory commitmentCategory) {
        return new ModelMapper().map(commitmentCategory, CommitmentCategoryDTO.class);
    }

    public CommitmentCategory toDomainObj() {
        return new ModelMapper().map(this, CommitmentCategory.class);
    }
}
