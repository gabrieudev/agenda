package br.com.gabrieudev.agenda.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.agenda.application.gateways.CommitmentGateway;
import br.com.gabrieudev.agenda.application.gateways.StatusGateway;
import br.com.gabrieudev.agenda.application.gateways.TaskGateway;
import br.com.gabrieudev.agenda.application.usecases.StatusInteractor;
import br.com.gabrieudev.agenda.infrastructrure.gateways.StatusServiceGateway;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.StatusRepository;

@Configuration
public class StatusConfig {
    @Bean
    StatusInteractor statusInteractor(StatusGateway statusGateway, CommitmentGateway commitmentGateway, TaskGateway taskGateway) {
        return new StatusInteractor(statusGateway, commitmentGateway, taskGateway);
    }

    @Bean
    StatusGateway statusGateway(StatusRepository statusRepository) {
        return new StatusServiceGateway(statusRepository);
    }
}
