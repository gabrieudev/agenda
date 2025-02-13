package br.com.gabrieudev.agenda.infrastructrure.persistence.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.agenda.domain.entities.Notification;
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
@Table(name = "notifications")
public class NotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "message", nullable = false)
    private String message;
    
    @ManyToOne
    @JoinColumn(name = "commitment_id")
    private CommitmentModel commitment;
    
    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "is_sended", nullable = false)
    private Boolean isSended;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static NotificationModel from(Notification notification) {
        return new ModelMapper().map(notification, NotificationModel.class);
    }

    public void update(Notification notification) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(notification, this);
    }

    public Notification toDomainObj() {
        return new ModelMapper().map(this, Notification.class);
    }
}
