package com.habits.habits_manager.user.service;

import com.habits.habits_manager.user.model.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateAccessToken(UserModel user) {
        Instant expirationDate = this.generateAccessExpirationDate();

        return this.generateToken(user.getUsername(), expirationDate);
    }

    public String generateRefreshToken(UserModel user) {
        Instant expirationDate = this.generateRefreshExpirationDate();

        return this.generateToken(user.getUsername(), expirationDate);
    }

    public String generateToken(String username, Instant expirationDate) {
        if (this.secret == null || this.secret.isEmpty()) {
            throw new IllegalArgumentException("The Secret cannot be null or empty");
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);

            return JWT.create()
                    .withIssuer("auth0")
                    .withSubject(username)
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);

            return JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    private Instant generateAccessExpirationDate() {

        return Instant.now().plusMillis(1000 * 60 * 10);
    }

    private Instant generateRefreshExpirationDate() {

        return Instant.now().plusMillis(1000 * 60 * 60);
    }
}
