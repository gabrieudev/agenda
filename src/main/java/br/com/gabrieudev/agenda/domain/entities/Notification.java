package br.com.gabrieudev.agenda.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
    private UUID id;
    private String message;
    private User user;
    private Boolean isSended;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
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
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    public Boolean getIsSended() {
        return isSended;
    }
    public void setIsSended(Boolean isSended) {
        this.isSended = isSended;
    }
    
    public Notification(UUID id, String message, User user, Boolean isSended, LocalDateTime dueDate,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.message = message;
        this.user = user;
        this.isSended = isSended;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Notification() {
    }
}
