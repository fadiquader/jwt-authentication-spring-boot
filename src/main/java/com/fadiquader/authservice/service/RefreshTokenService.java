package com.fadiquader.authservice.service;

import java.time.Instant;

public interface RefreshTokenService {
    String createRefreshToken(String username);
    String refreshToken(String token, String username);
}
