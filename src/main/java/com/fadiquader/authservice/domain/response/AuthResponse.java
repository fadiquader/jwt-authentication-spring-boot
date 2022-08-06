package com.fadiquader.authservice.domain.response;

import com.fadiquader.authservice.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private UserEntity user;
}
