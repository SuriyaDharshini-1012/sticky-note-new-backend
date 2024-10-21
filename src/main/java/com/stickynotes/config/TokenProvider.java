package com.stickynotes.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.stickynotes.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenProvider
{
    @Value("${security.jwt.secret-key}")
    private String jwtSecret;

    public String generateAccessToken(User user) {
        try {
            String username = user.getUsername();
            String firstName = user.getFirstName();

            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create()
                    .withSubject(username)
                    .withClaim("UserEmail", username)
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(genAccessExpirationDate())
                    .withClaim("Name", firstName)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error while generating access token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algorithm).build().verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error while validating token", exception);
        }
    }
    public String refreshAccessToken(String refreshToken) {
        String email = validateRefreshToken(refreshToken);
        User user = new User(null, email, null, null,null);
        return generateAccessToken(user);
    }
    public String generateRefreshToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create().withSubject(user.getUsername()).withClaim("username", user.getUsername())
                    .withExpiresAt(genRefreshExpirationDate()).sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error while generating refresh token", exception);
        }
    }
    public String validateRefreshToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algorithm).build().verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error while validating refresh token", exception);
        }
    }
    private Instant genAccessExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant genRefreshExpirationDate() {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC);
    }
}
