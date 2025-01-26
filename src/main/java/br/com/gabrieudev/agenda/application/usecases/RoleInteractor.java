package br.com.gabrieudev.agenda.application.usecases;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.application.exceptions.BusinessRuleException;
import br.com.gabrieudev.agenda.application.exceptions.EntityAlreadyExistsException;
import br.com.gabrieudev.agenda.application.gateways.RoleGateway;
import br.com.gabrieudev.agenda.application.gateways.UsersRolesGateway;
import br.com.gabrieudev.agenda.domain.entities.Role;

public class RoleInteractor {
    private final RoleGateway roleGateway;
    private final UsersRolesGateway usersRolesGateway;

    public RoleInteractor(RoleGateway roleGateway, UsersRolesGateway usersRolesGateway) {
        this.roleGateway = roleGateway;
        this.usersRolesGateway = usersRolesGateway;
    }

    public void delete(UUID id) {
        if (usersRolesGateway.existsByRoleId(id)) {
            throw new BusinessRuleException("Não é possível excluir uma role vinculada a um usuário");
        }

        roleGateway.delete(id);
    }

    public Role save(Role role) {
        if (existsByName(role.getName())) {
            throw new EntityAlreadyExistsException("Role já cadastrada com esse nome");
        }
        return roleGateway.create(role);
    }

    public Role update(Role role) {
        Role roleToUpdate = roleGateway.findById(role.getId());

        if (!roleToUpdate.getName().equals(role.getName()) && existsByName(role.getName())) {
            throw new EntityAlreadyExistsException("Role já cadastrada com esse nome");
        }

        return roleGateway.update(role);
    }

    public Role findById(UUID id) {
        return roleGateway.findById(id);
    }

    public boolean existsById(UUID id) {
        return roleGateway.existsById(id);
    }

    public boolean existsByName(String name) {
        return roleGateway.existsByName(name);
    }

    public List<Role> getAllRoles(Integer page, Integer size) {
        return roleGateway.getAllRoles(page, size);
    }

    public List<Role> getAllRolesByUserId(UUID userId) {
        return roleGateway.getAllRolesByUserId(userId);
    }
}
