package br.com.gabrieudev.agenda.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.agenda.application.gateways.CommitmentCategoryGateway;
import br.com.gabrieudev.agenda.application.gateways.CommitmentGateway;
import br.com.gabrieudev.agenda.application.usecases.CommitmentCategoryInteractor;
import br.com.gabrieudev.agenda.infrastructrure.gateways.CommitmentCategoryServiceGateway;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.CommitmentCategoryRepository;

@Configuration
public class CommitmentCategoryConfig {
    @Bean
    CommitmentCategoryInteractor commitmentCategoryInteractor(CommitmentCategoryGateway commitmentCategoryGateway, CommitmentGateway commitmentGateway) {
        return new CommitmentCategoryInteractor(commitmentCategoryGateway, commitmentGateway);
    }

    @Bean
    CommitmentCategoryGateway commitmentCategoryGateway(CommitmentCategoryRepository commitmentCategoryRepository) {
        return new CommitmentCategoryServiceGateway(commitmentCategoryRepository);
    }
}
