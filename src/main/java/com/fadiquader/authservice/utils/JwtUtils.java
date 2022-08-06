package com.fadiquader.authservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    @Value("${app.jwt.access-token.secret}")
    private String accessTokenSecret;
    @Value("${app.jwt.refresh-token.secret}")
    private String refreshTokenSecret;
    @Value("${app.jwt.access-token.expirationMs}")
    private Integer accessTokenExpirationMs;
    @Value("${app.jwt.refresh-token.expirationMs}")
    private Integer refreshTokenExpirationMs = 30 * 60 * 1000;

    private Algorithm getAlgorithm(String jwtSecret) {
        return Algorithm.HMAC256(jwtSecret.getBytes());
    }
    private String generateToken(String jwtSecret, int expMs, String username) {
        log.info("jwtSecret {}, expMs {}, username {}", jwtSecret, expMs, username);
        Algorithm algorithm = getAlgorithm(jwtSecret);
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expMs))
                .withIssuer("localhost:8080")
                .sign(algorithm);
    }

    public String generateAccessToken(String username) {
        return generateToken(accessTokenSecret, accessTokenExpirationMs, username);
    }
    public String generateRefreshToken(String username) {
        return generateToken(refreshTokenSecret, refreshTokenExpirationMs, username);
    }

    private DecodedJWT verifyToken(String jwtSecret, String token) {
        Algorithm algorithm = getAlgorithm(jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            return verifier.verify(token);
        } catch (Exception ex) {
            log.error("Error verifying jwt token {}", ex.getMessage());
            return null;
        }
    }

    public DecodedJWT verifyAccessToken(String token) {
        return verifyToken(accessTokenSecret, token);
    }

    public DecodedJWT verifyRefreshToken(String token) {
        log.info("Refresh Token {}", token);
        return verifyToken(refreshTokenSecret, token);
    }

}
