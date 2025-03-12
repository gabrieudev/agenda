package br.com.gabrieudev.agenda.application.usecases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.application.exceptions.EntityAlreadyExistsException;
import br.com.gabrieudev.agenda.application.gateways.CommitmentGateway;
import br.com.gabrieudev.agenda.application.gateways.NotificationGateway;
import br.com.gabrieudev.agenda.application.gateways.NotificationGuestGateway;
import br.com.gabrieudev.agenda.application.gateways.RoleGateway;
import br.com.gabrieudev.agenda.application.gateways.TaskGateway;
import br.com.gabrieudev.agenda.application.gateways.UserGateway;
import br.com.gabrieudev.agenda.application.gateways.UsersRolesGateway;
import br.com.gabrieudev.agenda.domain.entities.Commitment;
import br.com.gabrieudev.agenda.domain.entities.Notification;
import br.com.gabrieudev.agenda.domain.entities.NotificationGuest;
import br.com.gabrieudev.agenda.domain.entities.Role;
import br.com.gabrieudev.agenda.domain.entities.Task;
import br.com.gabrieudev.agenda.domain.entities.User;
import br.com.gabrieudev.agenda.domain.entities.UsersRoles;

public class UserInteractor {
    private final UserGateway userGateway;
    private final RoleGateway roleGateway;
    private final UsersRolesGateway usersRolesGateway;
    private final CommitmentGateway commitmentGateway;
    private final TaskGateway taskGateway;
    private final NotificationGateway notificationGateway;
    private final NotificationGuestGateway notificationGuestGateway;
    
    public UserInteractor(UserGateway userGateway, RoleGateway roleGateway, UsersRolesGateway usersRolesGateway, CommitmentGateway commitmentGateway, TaskGateway taskGateway, NotificationGateway notificationGateway, NotificationGuestGateway notificationGuestGateway) {
        this.userGateway = userGateway;
        this.roleGateway = roleGateway;
        this.usersRolesGateway = usersRolesGateway;
        this.commitmentGateway = commitmentGateway;
        this.taskGateway = taskGateway;
        this.notificationGateway = notificationGateway;
        this.notificationGuestGateway = notificationGuestGateway;
    }

    public User signup(User user) {
        if (userGateway.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException("E-mail já cadastrado");
        }

        user.setPassword(userGateway.encode(user.getPassword()));
        
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setIsActive(Boolean.FALSE);

        User createdUser = userGateway.signup(user);

        Role basicRole = roleGateway.findByName("USER");
        
        usersRolesGateway.create(new UsersRoles(null, createdUser, basicRole));

        sendConfirmationEmail(createdUser.getId());
        
        return createdUser;
    }

    public void confirm(UUID code) {
        userGateway.confirm(code);
    }

    public void sendConfirmationEmail(UUID id) {
        userGateway.sendConfirmationEmail(id);
    }

    public User findById(UUID id) {
        return userGateway.findById(id);
    }

    public User update(User user) {
        User userToUpdate = userGateway.findById(user.getId());

        if (!userToUpdate.getEmail().equals(user.getEmail()) && userGateway.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException("E-mail já cadastrado");
        }

        user.setPassword(userGateway.encode(user.getPassword()));

        user.setUpdatedAt(LocalDateTime.now());

        return userGateway.update(user);
    }

    public void delete(UUID id) {
        List<UsersRoles> usersRoles = usersRolesGateway.findByUserId(id);

        List<Commitment> commitments = commitmentGateway.findByUserId(id, null, null, null, 0, Integer.MAX_VALUE);

        commitments.forEach(commitment -> {
            List<Task> tasks = taskGateway.findByCommitmentId(commitment.getId(), null, null);
            
            tasks.forEach(task -> taskGateway.deleteById(task.getId()));

            List<Notification> notifications = notificationGateway.findByCommitmentId(commitment.getId(), 0, Integer.MAX_VALUE);

            notifications.forEach(notification -> {
                List<NotificationGuest> guests = notificationGuestGateway.findAllByCriteria(null, null, notification.getId(), 0, Integer.MAX_VALUE);
                
                guests.forEach(guest -> notificationGuestGateway.deleteById(guest.getId()));

                notificationGateway.deleteById(notification.getId());
            });

            commitmentGateway.deleteById(commitment.getId());
        });

        usersRoles.forEach(userRole -> {
            usersRolesGateway.deleteById(userRole.getId());
        });

        userGateway.delete(id);
    }

    public List<User> search(String param, String email, Integer page, Integer size) {
        return userGateway.search(param, email, page, size);
    }

    public User findByEmail(String email) {
        return userGateway.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userGateway.existsByEmail(email);
    }

    public User findByToken(String token) {
        return userGateway.findByToken(token);
    }
}
