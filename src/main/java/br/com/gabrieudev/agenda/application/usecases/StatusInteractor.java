package br.com.gabrieudev.agenda.application.usecases;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.application.exceptions.BusinessRuleException;
import br.com.gabrieudev.agenda.application.exceptions.EntityAlreadyExistsException;
import br.com.gabrieudev.agenda.application.gateways.CommitmentGateway;
import br.com.gabrieudev.agenda.application.gateways.StatusGateway;
import br.com.gabrieudev.agenda.application.gateways.TaskGateway;
import br.com.gabrieudev.agenda.domain.entities.Status;

public class StatusInteractor {
    private final StatusGateway statusGateway;
    private final CommitmentGateway commitmentGateway;
    private final TaskGateway taskGateway;

    public StatusInteractor(StatusGateway statusGateway, CommitmentGateway commitmentGateway, TaskGateway taskGateway) {
        this.statusGateway = statusGateway;
        this.commitmentGateway = commitmentGateway;
        this.taskGateway = taskGateway;
    }

    public Status create(Status status) {
        if (existsByName(status.getName())) {
            throw new EntityAlreadyExistsException("Status com esse nome já cadastrado");
        }

        return statusGateway.create(status);
    }

    public Status update(Status status) {
        Status statusToUpdate = findById(status.getId());

        if (existsByName(status.getName()) && !statusToUpdate.getName().equals(status.getName())) {
            throw new EntityAlreadyExistsException("Status com esse nome já cadastrado");
        }

        return statusGateway.update(status);
    }

    public Status findById(UUID id) {
        return statusGateway.findById(id);
    }

    public Status findByName(String name) {
        return statusGateway.findByName(name);
    }

    public boolean existsById(UUID id) {
        return statusGateway.existsById(id);
    }

    public void deleteById(UUID id) {
        if (commitmentGateway.existsByStatusId(id)) {
            throw new BusinessRuleException("Status não pode ser deletado pois possui compromissos associados");
        }

        if (taskGateway.existsByStatusId(id)) {
            throw new BusinessRuleException("Status não pode ser deletado pois possui tarefas associadas");
        }

        statusGateway.deleteById(id);
    }

    public boolean existsByName(String name) {
        return statusGateway.existsByName(name);
    }

    public List<Status> findAll() {
        return statusGateway.findAll();
    }
}
