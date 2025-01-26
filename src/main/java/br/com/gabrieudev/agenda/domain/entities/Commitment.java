package br.com.gabrieudev.agenda.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Commitment {
    private UUID id;
    private String title;
    private String description;
    private CommitmentCategory category;
    private Status status;
    private User user;
    private LocalDateTime dueDate;
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
    public CommitmentCategory getCategory() {
        return category;
    }
    public void setCategory(CommitmentCategory category) {
        this.category = category;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
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

    public Commitment(UUID id, String title, String description, CommitmentCategory category, Status status, User user,
            LocalDateTime dueDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = status;
        this.user = user;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Commitment() {
    }
}
