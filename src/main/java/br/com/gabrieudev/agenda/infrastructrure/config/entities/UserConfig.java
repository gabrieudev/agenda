package br.com.gabrieudev.agenda.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import br.com.gabrieudev.agenda.application.gateways.RoleGateway;
import br.com.gabrieudev.agenda.application.gateways.UserGateway;
import br.com.gabrieudev.agenda.application.gateways.UsersRolesGateway;
import br.com.gabrieudev.agenda.application.usecases.UserInteractor;
import br.com.gabrieudev.agenda.infrastructrure.gateways.UserServiceGateway;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.UserRepository;

@Configuration
public class UserConfig {
    @Bean
    UserInteractor userInteractor(UserGateway userGateway, RoleGateway roleGateway, UsersRolesGateway usersRolesGateway) {
        return new UserInteractor(userGateway, roleGateway, usersRolesGateway);
    }

    @Bean
    UserGateway userGateway(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtDecoder jwtDecoder) {
        return new UserServiceGateway(userRepository, passwordEncoder, jwtDecoder);
    }
}
