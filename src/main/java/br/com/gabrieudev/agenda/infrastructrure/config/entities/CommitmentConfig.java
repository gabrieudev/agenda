package br.com.gabrieudev.agenda.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import br.com.gabrieudev.agenda.application.gateways.CommitmentGateway;
import br.com.gabrieudev.agenda.application.gateways.StatusGateway;
import br.com.gabrieudev.agenda.application.gateways.TaskGateway;
import br.com.gabrieudev.agenda.application.usecases.CommitmentInteractor;
import br.com.gabrieudev.agenda.infrastructrure.gateways.CommitmentServiceGateway;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.CommitmentCategoryRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.CommitmentRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.StatusRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.UserRepository;

@Configuration
public class CommitmentConfig {
    @Bean
    CommitmentInteractor commitmentInteractor(CommitmentGateway commitmentGateway, StatusGateway statusGateway, TaskGateway taskGateway) {
        return new CommitmentInteractor(commitmentGateway, statusGateway, taskGateway);
    }

    @Bean
    CommitmentGateway commitmentGateway(CommitmentRepository commitmentRepository, StatusRepository statusRepository, CommitmentCategoryRepository commitmentCategoryRepository, UserRepository userRepository, JwtDecoder jwtDecoder) {
        return new CommitmentServiceGateway(commitmentRepository, commitmentCategoryRepository, statusRepository, userRepository, jwtDecoder);
    }
}
