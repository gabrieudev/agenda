package br.com.gabrieudev.agenda.infrastructrure.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.agenda.infrastructrure.persistence.models.RoleModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.UserModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.UsersRolesModel;

@Repository
public interface UsersRolesRepository extends JpaRepository<UsersRolesModel, UUID> {
    List<UsersRolesModel> findByUser(UserModel user);
    boolean existsByUserAndRole(UserModel user, RoleModel role);
    boolean existsByRole(RoleModel role);
    void deleteByUserAndRole(UserModel user, RoleModel role);
}