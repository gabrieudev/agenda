package br.com.gabrieudev.agenda.application.gateways;

import java.util.Map;

import br.com.gabrieudev.agenda.domain.entities.User;

public interface AuthGateway {
    String generateAccessToken(User user);
    Map<String, String> refresh (String refreshToken);
    String generateRefreshToken(User user);
    boolean isValid(String token);
    void logout(String refreshToken, String accessToken);
}
