package com.fadiquader.authservice.service;

import com.fadiquader.authservice.domain.entity.RefreshTokenEntity;
import com.fadiquader.authservice.domain.entity.UserEntity;
import com.fadiquader.authservice.repository.RefreshTokenRepository;
import com.fadiquader.authservice.repository.UserRepository;
import com.fadiquader.authservice.service.impl.RefreshTokenServiceImpl;
import com.fadiquader.authservice.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestRefreshTokenServiceImpl {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private JwtUtils jwtUtils;
    @InjectMocks
    private RefreshTokenServiceImpl refreshTokenService;
    RefreshTokenEntity refreshToken;
    @BeforeEach
    void setUp() {
        when(jwtUtils.generateRefreshToken(anyString())).thenReturn("jwt.token");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new UserEntity()));
        refreshToken = new RefreshTokenEntity();
        refreshToken.setIsValid(true);
    }

    @Test
    void createRefreshToken() {
        when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenReturn(refreshToken);
        assertEquals(refreshTokenService.createRefreshToken("user"), "jwt.token");
    }


    @Test
    void refreshToken() {
        when(refreshTokenRepository.findByTokenAndIsValid("jwt.token", true)).thenReturn(Optional.of(refreshToken));
        when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenReturn(refreshToken);
        String jwtToken = refreshTokenService.refreshToken("jwt.token", "user");
        assertNotNull(jwtToken);
        assertFalse(refreshToken.getIsValid());
    }

}
