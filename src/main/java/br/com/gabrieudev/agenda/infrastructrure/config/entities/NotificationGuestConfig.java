package br.com.gabrieudev.agenda.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.agenda.application.gateways.NotificationGuestGateway;
import br.com.gabrieudev.agenda.application.gateways.StatusGateway;
import br.com.gabrieudev.agenda.application.usecases.NotificationGuestInteractor;
import br.com.gabrieudev.agenda.infrastructrure.gateways.NotificationGuestServiceGateway;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.NotificationGuestRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.StatusRepository;

@Configuration
public class NotificationGuestConfig {
    @Bean
    NotificationGuestInteractor notificationGuestInteractor(NotificationGuestGateway notificationGuestGateway, StatusGateway statusGateway) {
        return new NotificationGuestInteractor(notificationGuestGateway, statusGateway);
    }

    @Bean
    NotificationGuestGateway notificationGuestGateway(NotificationGuestRepository notificationGuestRepository, StatusRepository statusRepository) {
        return new NotificationGuestServiceGateway(notificationGuestRepository, statusRepository);
    }
}
