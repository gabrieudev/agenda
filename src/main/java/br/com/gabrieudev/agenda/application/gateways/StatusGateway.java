package br.com.gabrieudev.agenda.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.agenda.domain.entities.Status;

public interface StatusGateway {
    Status create(Status status);
    Status update(Status status);
    Status findById(UUID id);
    Status findByName(String name);
    boolean existsById(UUID id);
    boolean existsByName(String name);
    void deleteById(UUID id);
    List<Status> findAll();
}
