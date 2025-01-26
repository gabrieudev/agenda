package br.com.gabrieudev.agenda.infrastructrure.persistence.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Commitment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "commitments")
public class CommitmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "commitment_category_id")
    private CommitmentCategoryModel category;
    
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusModel status;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
    
    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static CommitmentModel from(Commitment commitment) {
        return new ModelMapper().map(commitment, CommitmentModel.class);
    }

    public void update(Commitment commitment) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(commitment, this);
    }

    public Commitment toDomainObj() {
        return new ModelMapper().map(this, Commitment.class);
    }
}
