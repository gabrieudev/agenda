package br.com.gabrieudev.agenda.application.gateways;

import br.com.gabrieudev.agenda.domain.entities.User;

public interface AuthGateway {
    String generateAccessToken(User user);
    String refresh (String refreshToken);
    String generateRefreshToken(User user);
    boolean isValid(String token);
    void logout(String refreshToken, String accessToken);
}
