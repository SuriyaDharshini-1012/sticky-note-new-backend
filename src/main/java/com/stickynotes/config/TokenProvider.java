package com.stickynotes.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
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
    private String JWT_SECRET;


public String generateAccessToken(User user) {
    try {
        String username = user.getUsername();
        String firstName = user.getFirstName();
        String userId = user.getId();

        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        return JWT.create()
                .withSubject(username)
                .withClaim("UserEmail", username)
                .withIssuedAt(Instant.now())
                .withExpiresAt(genAccessExperationDate())
                .withClaim("FirstName", firstName)
                .withClaim("UserId", userId)
                .sign(algorithm);
    } catch (JWTCreationException exception) {
        throw new JWTCreationException("Error while generating access token", exception);
    }
}

    public String getUserIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);

            // Check if the 'UserId' claim is present
            String userId = decodedJWT.getClaim("UserId").asString();
            if (userId == null) {
                throw new JWTVerificationException("UserId claim is missing from the token");
            }
            return userId;
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error while validating token: " + exception.getMessage(), exception);
        }
    }


    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm).build().verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error while validating token", exception);
        }
    }
    public String refreshAccessToken(String refreshToken) {
        String email = validateRefreshToken(refreshToken);
        User user = new User();
        return generateAccessToken(user);
    }
    public String generateRefreshToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.create().withSubject(user.getUsername()).withClaim("username", user.getUsername())
                    .withExpiresAt(genRefreshExperationDate()).sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error while generating refresh token", exception);
        }
    }
    public String validateRefreshToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm).build().verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error while validating refresh token", exception);
        }
    }
    public Instant genAccessExperationDate() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.UTC); // 24 hours expiration time
    }


    public Instant genRefreshExperationDate() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.UTC);

    }

}
