package br.com.gabrieudev.agenda.application.gateways;

import java.util.UUID;

import br.com.gabrieudev.agenda.domain.entities.UsersRoles;

public interface UsersRolesGateway {
    UsersRoles create(UsersRoles usersRoles);
    void deleteByUserIdAndRoleId(UUID userId, UUID roleId);
    boolean existsById(UUID id);
    boolean existsByUserIdAndRoleId(UUID userId, UUID roleId);
    boolean existsByRoleId(UUID roleId);
    UsersRoles findById(UUID id);
}
