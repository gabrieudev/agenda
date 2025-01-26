package br.com.gabrieudev.agenda.infrastructrure.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.agenda.infrastructrure.persistence.models.CommitmentCategoryModel;

@Repository
public interface CommitmentCategoryRepository extends JpaRepository<CommitmentCategoryModel, UUID> {
    Optional<CommitmentCategoryModel> findByName(String name);
    boolean existsByName(String name);
}
