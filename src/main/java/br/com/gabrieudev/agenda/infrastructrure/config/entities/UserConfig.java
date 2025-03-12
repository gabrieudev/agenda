package br.com.gabrieudev.agenda.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import br.com.gabrieudev.agenda.application.gateways.CommitmentGateway;
import br.com.gabrieudev.agenda.application.gateways.NotificationGateway;
import br.com.gabrieudev.agenda.application.gateways.NotificationGuestGateway;
import br.com.gabrieudev.agenda.application.gateways.RoleGateway;
import br.com.gabrieudev.agenda.application.gateways.TaskGateway;
import br.com.gabrieudev.agenda.application.gateways.UserGateway;
import br.com.gabrieudev.agenda.application.gateways.UsersRolesGateway;
import br.com.gabrieudev.agenda.application.usecases.UserInteractor;
import br.com.gabrieudev.agenda.infrastructrure.gateways.UserServiceGateway;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.UserRepository;
import br.com.gabrieudev.agenda.infrastructrure.service.EmailService;

@Configuration
public class UserConfig {
    @Bean
    UserInteractor userInteractor(UserGateway userGateway, RoleGateway roleGateway, UsersRolesGateway usersRolesGateway, CommitmentGateway commitmentGateway, TaskGateway taskGateway, NotificationGateway notificationGateway, NotificationGuestGateway notificationGuestGateway) {
        return new UserInteractor(userGateway, roleGateway, usersRolesGateway, commitmentGateway, taskGateway, notificationGateway, notificationGuestGateway);
    }

    @Bean
    UserGateway userGateway(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtDecoder jwtDecoder, RedisTemplate<String, Object> redisTemplate, EmailService emailService) {
        return new UserServiceGateway(userRepository, passwordEncoder, jwtDecoder, redisTemplate, emailService);
    }
}
