package com.fadiquader.authservice.service.impl;

import com.fadiquader.authservice.domain.entity.RefreshTokenEntity;
import com.fadiquader.authservice.domain.entity.UserEntity;
import com.fadiquader.authservice.repository.RefreshTokenRepository;
import com.fadiquader.authservice.repository.UserRepository;
import com.fadiquader.authservice.service.RefreshTokenService;
import com.fadiquader.authservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;

    private void invalidateRefreshToken(String token) {
        RefreshTokenEntity oldToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        oldToken.setIsValid(false);
        refreshTokenRepository.save(oldToken);
    }
    @Override
    public String createRefreshToken(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        String refreshTokenJwt = jwtUtils.generateRefreshToken(username);
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setToken(refreshTokenJwt);
        refreshToken.setIsValid(true);
        refreshToken.setUser(user);
        refreshTokenRepository.save(refreshToken);

        return refreshTokenJwt;
    }

    @Override
    public String refreshToken(String token, String username) {
        invalidateRefreshToken(token);
        return createRefreshToken(username);
    }
}
