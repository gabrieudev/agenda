package br.com.gabrieudev.agenda.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.domain.entities.CommitmentCategory;

public interface CommitmentCategoryGateway {
    CommitmentCategory create(CommitmentCategory commitmentCategory);
    CommitmentCategory update(CommitmentCategory commitmentCategory);
    CommitmentCategory findById(UUID id);
    CommitmentCategory findByName(String name);
    boolean existsById(UUID id);
    boolean existsByName(String name);
    void deleteById(UUID id);
    List<CommitmentCategory> findAll();
}
