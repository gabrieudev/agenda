package br.com.gabrieudev.agenda.application.usecases;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.application.exceptions.BusinessRuleException;
import br.com.gabrieudev.agenda.application.exceptions.EntityAlreadyExistsException;
import br.com.gabrieudev.agenda.application.gateways.CommitmentCategoryGateway;
import br.com.gabrieudev.agenda.application.gateways.CommitmentGateway;
import br.com.gabrieudev.agenda.domain.entities.CommitmentCategory;

public class CommitmentCategoryInteractor {
    private final CommitmentCategoryGateway commitmentCategoryGateway;
    private final CommitmentGateway commitmentGateway;

    public CommitmentCategoryInteractor(CommitmentCategoryGateway commitmentCategoryGateway, CommitmentGateway commitmentGateway) {
        this.commitmentCategoryGateway = commitmentCategoryGateway;
        this.commitmentGateway = commitmentGateway;
    }

    public CommitmentCategory create(CommitmentCategory commitmentCategory) {
        if (existsByName(commitmentCategory.getName())) {
            throw new EntityAlreadyExistsException("Categoria de compromisso já cadastrada");
        }

        return commitmentCategoryGateway.create(commitmentCategory);
    }

    public CommitmentCategory update(CommitmentCategory commitmentCategory) {
        CommitmentCategory commitmentCategoryToUpdate = findById(commitmentCategory.getId());

        if (existsByName(commitmentCategory.getName()) && !commitmentCategoryToUpdate.getName().equals(commitmentCategory.getName())) {
            throw new EntityAlreadyExistsException("Categoria de compromisso já cadastrada");
        }

        return commitmentCategoryGateway.update(commitmentCategory);
    }

    public CommitmentCategory findById(UUID id) {
        return commitmentCategoryGateway.findById(id);
    }

    public CommitmentCategory findByName(String name) {
        return commitmentCategoryGateway.findByName(name);
    }

    public boolean existsById(UUID id) {
        return commitmentCategoryGateway.existsById(id);
    }

    public boolean existsByName(String name) {
        return commitmentCategoryGateway.existsByName(name);
    }

    public void deleteById(UUID id) {
        if (commitmentGateway.existsByCommitmentCategoryId(id)) {
            throw new BusinessRuleException("Categoria de compromisso possui compromissos cadastrados");
        }

        commitmentCategoryGateway.deleteById(id);
    }

    public List<CommitmentCategory> findAll() {
        return commitmentCategoryGateway.findAll();
    }
}
