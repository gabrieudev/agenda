package br.com.gabrieudev.agenda.infrastructrure.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.agenda.infrastructrure.persistence.models.StatusModel;

@Repository
public interface StatusRepository extends JpaRepository<StatusModel, UUID> {
    Optional<StatusModel> findByName(String name);
    boolean existsByName(String name);
}
