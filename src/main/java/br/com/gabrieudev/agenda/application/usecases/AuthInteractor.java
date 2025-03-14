package br.com.gabrieudev.agenda.application.usecases;

import java.util.Map;

import br.com.gabrieudev.agenda.application.exceptions.BadCredentialsException;
import br.com.gabrieudev.agenda.application.exceptions.InvalidTokenException;
import br.com.gabrieudev.agenda.application.gateways.AuthGateway;
import br.com.gabrieudev.agenda.application.gateways.UserGateway;
import br.com.gabrieudev.agenda.domain.entities.User;

public class AuthInteractor {
    private final AuthGateway authGateway;
    private final UserGateway userGateway;

    public AuthInteractor(AuthGateway authGateway, UserGateway userGateway) {
        this.authGateway = authGateway;
        this.userGateway = userGateway;
    }

    public Map<String, String> refresh(String refreshToken) {
        if (!authGateway.isValid(refreshToken)) {
            throw new InvalidTokenException("Token inválido");
        }

        return authGateway.refresh(refreshToken);
    }

    public Map<String, String> signin(String email, String password) {
        User user = userGateway.findByEmail(email);
        
        if (!userGateway.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        String token = authGateway.generateAccessToken(user);
        String refreshToken = authGateway.generateRefreshToken(user);

        return Map.of("token", token, "refreshToken", refreshToken);
    }

    public void logout(String refreshToken, String accessToken) {
        authGateway.logout(refreshToken, accessToken);
    }
}
