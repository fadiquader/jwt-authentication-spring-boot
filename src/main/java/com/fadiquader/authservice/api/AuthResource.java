package com.fadiquader.authservice.api;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fadiquader.authservice.domain.request.RefreshTokenRequest;
import com.fadiquader.authservice.domain.request.UserLoginRequest;
import com.fadiquader.authservice.domain.response.AuthResponse;
import com.fadiquader.authservice.security.UserPrincipal;
import com.fadiquader.authservice.service.RefreshTokenService;
import com.fadiquader.authservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthResource {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest loginRequest) {
        return ResponseEntity.ok(authenticate(loginRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        String token = request.getToken();
        DecodedJWT decodedJWT = jwtUtils.verifyRefreshToken(token);
        if (decodedJWT == null) throw new RuntimeException("Invalid token");

        String username = decodedJWT.getSubject();
        String refreshToken = refreshTokenService.refreshToken(token, username);
        String accessToken = jwtUtils.generateAccessToken(username);
        log.info("Username {}", decodedJWT.getSubject());

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken, null));
    }

    private AuthResponse authenticate(UserLoginRequest loginRequest) throws RuntimeException {
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if (authentication == null || !authentication.isAuthenticated()) throw new RuntimeException("AuthenticationFailed");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        String accessToken = jwtUtils.generateAccessToken(username);
        String refreshToken = refreshTokenService.createRefreshToken(username);

        return new AuthResponse(accessToken, refreshToken, userPrincipal.getUserEntity());
    }
}
