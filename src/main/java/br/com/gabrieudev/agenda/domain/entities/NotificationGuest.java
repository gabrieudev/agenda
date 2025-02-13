package br.com.gabrieudev.agenda.domain.entities;

import java.util.UUID;

public class NotificationGuest {
    private UUID id;
    private Status status;
    private User user;
    private Notification notification;
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
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
    public Notification getNotification() {
        return notification;
    }
    public void setNotification(Notification notification) {
        this.notification = notification;
    }
    
    public NotificationGuest(UUID id, Status status, User user, Notification notification) {
        this.id = id;
        this.status = status;
        this.user = user;
        this.notification = notification;
    }
    
    public NotificationGuest() {
    }
}
