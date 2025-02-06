package br.com.gabrieudev.agenda.application.usecases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.application.exceptions.EntityAlreadyExistsException;
import br.com.gabrieudev.agenda.application.gateways.RoleGateway;
import br.com.gabrieudev.agenda.application.gateways.UserGateway;
import br.com.gabrieudev.agenda.application.gateways.UsersRolesGateway;
import br.com.gabrieudev.agenda.domain.entities.Role;
import br.com.gabrieudev.agenda.domain.entities.User;
import br.com.gabrieudev.agenda.domain.entities.UsersRoles;

public class UserInteractor {
    private final UserGateway userGateway;
    private final RoleGateway roleGateway;
    private final UsersRolesGateway usersRolesGateway;
    
    public UserInteractor(UserGateway userGateway, RoleGateway roleGateway, UsersRolesGateway usersRolesGateway) {
        this.userGateway = userGateway;
        this.roleGateway = roleGateway;
        this.usersRolesGateway = usersRolesGateway;
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
        userGateway.delete(id);
    }

    public List<User> search(String param, Integer page, Integer size) {
        return userGateway.search(param, page, size);
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
