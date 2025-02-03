package br.com.gabrieudev.agenda.infrastructrure.gateways;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.agenda.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.agenda.application.gateways.TaskGateway;
import br.com.gabrieudev.agenda.domain.entities.Task;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.StatusModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.TaskModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.StatusRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.TaskRepository;

@Service
public class TaskServiceGateway implements TaskGateway {
    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;

    public TaskServiceGateway(TaskRepository taskRepository, StatusRepository statusRepository) {
        this.taskRepository = taskRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    @CacheEvict(value = "Tasks", key = "#task.commitmentId")
    @Transactional
    public Task create(Task task) {
        return taskRepository.save(TaskModel.from(task)).toDomainObj();
    }

    @Override
    @CacheEvict(value = "Tasks", key = "#id")
    @Transactional
    public void deleteById(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task não encontrado");
        }

        taskRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return taskRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByStatusId(UUID statusId) {
        StatusModel status = statusRepository.findById(statusId)
            .orElseThrow(() -> new EntityNotFoundException("Status não encontrado"));
        
        return taskRepository.existsByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByCommitmentId(UUID commitmentId, UUID statusId, String param) {
        return taskRepository.findByCommitmentId(commitmentId, statusId, param)
            .stream()
            .map(TaskModel::toDomainObj)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "Tasks", key = "#id")
    public Task findById(UUID id) {
        return taskRepository.findById(id)
            .map(TaskModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("Task não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByUserIdAndStatusIdAndUpdatedBetween(UUID userId, UUID statusId, LocalDateTime startDate, LocalDateTime endDate) {
        return taskRepository.findByUserIdAndStatusIdAndUpdatedBetween(userId, statusId, startDate, endDate)
            .stream()
            .map(TaskModel::toDomainObj)
            .toList();
    }

    @Override
    @CacheEvict(value = "Tasks", key = "#task.commitmentId")
    @Transactional
    public Task update(Task task) {
        if (!taskRepository.existsById(task.getId())) {
            throw new EntityNotFoundException("Task não encontrado");
        }

        TaskModel taskToUpdate = taskRepository.findById(task.getId())
            .orElseThrow(() -> new EntityNotFoundException("Task não encontrado"));

        taskToUpdate.update(task);

        return taskRepository.save(taskToUpdate).toDomainObj();
    }
}
