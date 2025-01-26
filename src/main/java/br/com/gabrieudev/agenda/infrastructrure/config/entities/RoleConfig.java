package br.com.gabrieudev.agenda.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.agenda.application.gateways.RoleGateway;
import br.com.gabrieudev.agenda.application.gateways.UsersRolesGateway;
import br.com.gabrieudev.agenda.application.usecases.RoleInteractor;
import br.com.gabrieudev.agenda.infrastructrure.gateways.RoleServiceGateway;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.RoleRepository;

@Configuration
public class RoleConfig {
    @Bean
    RoleInteractor roleInteractor(RoleGateway roleGateway, UsersRolesGateway usersRolesGateway) {
        return new RoleInteractor(roleGateway, usersRolesGateway);
    }

    @Bean
    RoleGateway roleGateway(RoleRepository roleRepository) {
        return new RoleServiceGateway(roleRepository);
    }
}
