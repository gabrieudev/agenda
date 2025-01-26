package br.com.gabrieudev.agenda.infrastructrure.gateways;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.agenda.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.agenda.application.gateways.StatusGateway;
import br.com.gabrieudev.agenda.domain.entities.Status;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.StatusModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.StatusRepository;

@Service
public class StatusServiceGateway implements StatusGateway {
    private final StatusRepository statusRepository;

    public StatusServiceGateway(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    @CacheEvict(value = "Statuses", key = "#status.id")
    @Transactional
    public Status create(Status status) {
        return statusRepository.save(StatusModel.from(status)).toDomainObj();
    }

    @Override
    @CacheEvict(value = "Statuses", key = "#id")
    @Transactional
    public void deleteById(UUID id) {
        if (!statusRepository.existsById(id)) {
            throw new EntityNotFoundException("Status não encontrado");
        }

        statusRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return statusRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return statusRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Status> findAll() {
        return statusRepository.findAll().stream()
            .map(StatusModel::toDomainObj)
            .toList();
    }

    @Override
    @Cacheable(value = "Statuses", key = "#id")
    @Transactional(readOnly = true)
    public Status findById(UUID id) {
        return statusRepository.findById(id)
            .map(StatusModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("Status não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public Status findByName(String name) {
        return statusRepository.findByName(name)
            .map(StatusModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("Status não encontrado"));
    }

    @Override
    @CacheEvict(value = "Statuses", key = "#status.id")
    @Transactional
    public Status update(Status status) {
        StatusModel statusToUpdate = statusRepository.findById(status.getId())
            .orElseThrow(() -> new EntityNotFoundException("Status não encontrado"));

        statusToUpdate.update(status);

        return statusRepository.save(statusToUpdate).toDomainObj();
    }
    
}
