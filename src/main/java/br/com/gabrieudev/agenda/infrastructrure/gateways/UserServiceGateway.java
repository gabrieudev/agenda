package br.com.gabrieudev.agenda.infrastructrure.gateways;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.agenda.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.agenda.application.exceptions.InvalidTokenException;
import br.com.gabrieudev.agenda.application.gateways.UserGateway;
import br.com.gabrieudev.agenda.domain.entities.User;
import br.com.gabrieudev.agenda.infrastructrure.persistence.models.UserModel;
import br.com.gabrieudev.agenda.infrastructrure.persistence.repositories.UserRepository;
import br.com.gabrieudev.agenda.infrastructrure.service.EmailService;

@Service
public class UserServiceGateway implements UserGateway {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtDecoder jwtDecoder;
    private final RedisTemplate<String, Object> redisTemplate;
    private final EmailService emailService;
    @Value("${api.base-url}")
    private String baseUrl;

    public UserServiceGateway(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtDecoder jwtDecoder, RedisTemplate<String, Object> redisTemplate, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtDecoder = jwtDecoder;
        this.redisTemplate = redisTemplate;
        this.emailService = emailService;
    }

    @Override
    @CacheEvict(value = "Users", key = "#id")
    @Transactional
    public void delete(UUID id) {
        if (!existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado.");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(UserModel::toDomainObj)    
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

    @Override
    @Cacheable(value = "Users", key = "#id")
    @Transactional(readOnly = true)
    public User findById(UUID id) {
        return userRepository.findById(id)
            .map(UserModel::toDomainObj)    
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> search(String param, String email, Integer page, Integer size) {
        return userRepository.findAll(param, email, PageRequest.of(page, size))
            .stream()
            .map(UserModel::toDomainObj)
            .toList();
    }

    @Override
    @CacheEvict(value = "Users", key = "#user.id")
    @Transactional
    public User signup(User user) {
        User savedUser = userRepository.save(UserModel.fromDomainObj(user)).toDomainObj();
        
        return savedUser;
    }

    @Override
    public void sendConfirmationEmail(UUID userId) {
        User user = findById(userId);

        UUID code = UUID.randomUUID();

        redisTemplate.opsForValue().set("code:" + code.toString(), user.getId().toString(), Duration.ofMinutes(5));

        String url = baseUrl + "/users/confirm?code=" + code.toString();

        String emailMessage = String.format("Olá, %s. Por favor, clique no link abaixo para confirmar seu cadastro: %s", user.getFirstName(), url);

        emailService.sendEmail(user.getEmail(), "Confirmação de cadastro", emailMessage);
    }

    @Override
    @Transactional
    public void confirm(UUID code) {
        String userId = (String) redisTemplate.opsForValue().get("code:" + code.toString());
    
        if (userId == null) {
            throw new EntityNotFoundException("Código de confirmação inválido.");
        }
    
        redisTemplate.delete("code:" + code.toString());
    
        User user = findById(UUID.fromString(userId));
    
        user.setIsActive(Boolean.TRUE);

        update(user);

        emailService.sendEmail(user.getEmail(), "Confirmação de cadastro", "Seu cadastro foi confirmado com sucesso.");
    }

    @Override
    @CacheEvict(value = "Users", key = "#user.id")
    @Transactional
    public User update(User user) {
        UserModel userToUpdate = userRepository.findById(user.getId())
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        userToUpdate.update(user);

        return userRepository.save(userToUpdate).toDomainObj();
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }

    @Override
    public User findByToken(String token) {
        try {
            var jwt = jwtDecoder.decode(token);
            String userId = jwt.getSubject();

            return findById(UUID.fromString(userId));
        } catch (Exception e) {
            throw new InvalidTokenException("Token inválido");
        }
    }

}
