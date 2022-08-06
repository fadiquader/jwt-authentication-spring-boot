package com.fadiquader.authservice.api;

import com.fadiquader.authservice.domain.request.UserLoginRequest;
import com.fadiquader.authservice.domain.response.AuthResponse;
import com.fadiquader.authservice.security.UserPrincipal;
import com.fadiquader.authservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
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
public class AuthResource {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authenticate(loginRequest));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private AuthResponse authenticate(UserLoginRequest loginRequest) throws RuntimeException {
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if (authentication != null && authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String accessToken = jwtUtils.generateAccessToken(userPrincipal.getUsername());
            String refreshToken = jwtUtils.generateRefreshToken(userPrincipal.getUsername());
            return new AuthResponse(accessToken, refreshToken, userPrincipal.getUserEntity());
        }

        throw new RuntimeException();

    }
}
