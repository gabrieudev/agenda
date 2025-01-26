package br.com.gabrieudev.agenda.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.agenda.application.gateways.StatusGateway;
import br.com.gabrieudev.agenda.application.gateways.TaskGateway;
import br.com.gabrieudev.agenda.application.usecases.TaskInteractor;
import br.com.gabrieudev.agenda.infrastructrure.gateways.TaskServiceGateway;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.StatusRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.TaskRepository;

@Configuration
public class TaskConfig {
    @Bean
    TaskInteractor taskInteractor(TaskGateway taskGateway, StatusGateway statusGateway) {
        return new TaskInteractor(taskGateway, statusGateway);
    }

    @Bean
    TaskGateway taskGateway(TaskRepository taskRepository, StatusRepository statusRepository) {
        return new TaskServiceGateway(taskRepository, statusRepository);
    }
}
