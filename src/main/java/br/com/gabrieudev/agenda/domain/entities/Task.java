package br.com.gabrieudev.agenda.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Task {
    private UUID id;
    private String title;
    private String description;
    private Commitment commitment;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Commitment getCommitment() {
        return commitment;
    }
    public void setCommitment(Commitment commitment) {
        this.commitment = commitment;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Task(UUID id, String title, String description, Commitment commitment, Status status,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.commitment = commitment;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Task() {
    }
}
