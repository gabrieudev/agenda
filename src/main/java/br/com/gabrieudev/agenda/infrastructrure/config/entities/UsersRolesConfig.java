package br.com.gabrieudev.agenda.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.agenda.application.gateways.UserGateway;
import br.com.gabrieudev.agenda.application.gateways.UsersRolesGateway;
import br.com.gabrieudev.agenda.application.usecases.UsersRolesInteractor;
import br.com.gabrieudev.agenda.infrastructrure.gateways.UsersRolesServiceGateway;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.RoleRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.UserRepository;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.UsersRolesRepository;

@Configuration
public class UsersRolesConfig {
    @Bean
    UsersRolesInteractor usersRolesInteractor(UsersRolesGateway usersRolesGateway, UserGateway userGateway) {
        return new UsersRolesInteractor(usersRolesGateway, userGateway);
    }

    @Bean
    UsersRolesGateway usersRolesGateway(UsersRolesRepository usersRolesRepository, UserRepository userRepository, RoleRepository roleRepository) {
        return new UsersRolesServiceGateway(usersRolesRepository, userRepository, roleRepository);
    }
}
