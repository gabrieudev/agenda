package br.com.gabrieudev.agenda.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.domain.entities.UsersRoles;

public interface UsersRolesGateway {
    UsersRoles create(UsersRoles usersRoles);
    List<UsersRoles> findByUserId(UUID userId);
    void deleteByUserIdAndRoleId(UUID userId, UUID roleId);
    void deleteById(UUID id);
    boolean existsById(UUID id);
    boolean existsByUserIdAndRoleId(UUID userId, UUID roleId);
    boolean existsByRoleId(UUID roleId);
    UsersRoles findById(UUID id);
}
