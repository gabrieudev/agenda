package br.com.gabrieudev.agenda.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.agenda.application.gateways.NotificationGateway;
import br.com.gabrieudev.agenda.application.usecases.NotificationInteractor;
import br.com.gabrieudev.agenda.infrastructrure.gateways.NotificationServiceGateway;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.CommitmentRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.NotificationRepository;

@Configuration
public class NotificationConfig {
    @Bean
    NotificationInteractor notificationInteractor(NotificationGateway notificationGateway) {
        return new NotificationInteractor(notificationGateway);
    }

    @Bean
    NotificationGateway notificationGateway(NotificationRepository notificationRepository, CommitmentRepository commitmentRepository) {
        return new NotificationServiceGateway(notificationRepository, commitmentRepository);
    }
}
