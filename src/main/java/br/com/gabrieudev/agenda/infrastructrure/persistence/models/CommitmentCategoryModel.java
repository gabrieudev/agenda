package br.com.gabrieudev.agenda.infrastructrure.persistence.models;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.CommitmentCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "commitment_categories")
public class CommitmentCategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    public static CommitmentCategoryModel from(CommitmentCategory commitmentCategory) {
        return new ModelMapper().map(commitmentCategory, CommitmentCategoryModel.class);
    }

    public void update(CommitmentCategory commitmentCategory) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(commitmentCategory, this);
    }

    public CommitmentCategory toDomainObj() {
        return new ModelMapper().map(this, CommitmentCategory.class);
    }
}
