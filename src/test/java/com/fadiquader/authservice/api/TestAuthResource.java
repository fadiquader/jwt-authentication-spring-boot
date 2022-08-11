package com.fadiquader.authservice.api;

import com.fadiquader.authservice.BaseConfiguration;
import com.fadiquader.authservice.domain.entity.UserEntity;
import com.fadiquader.authservice.domain.request.UserLoginRequest;
import com.fadiquader.authservice.security.AuthEntryPointJwt;
import com.fadiquader.authservice.security.SecurityConfiguration;
import com.fadiquader.authservice.security.UserPrincipal;
import com.fadiquader.authservice.service.RefreshTokenService;
import com.fadiquader.authservice.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(value = {BaseConfiguration.class, SecurityConfiguration.class})
@WebMvcTest(value = AuthResource.class)
public class TestAuthResource {
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private RefreshTokenService refreshTokenService;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper;
    UserEntity user;
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        user = initUser();
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new UserPrincipal(user));
        when(refreshTokenService.createRefreshToken(anyString())).thenReturn("12345678");
        when(jwtUtils.generateAccessToken(anyString())).thenReturn("1234");
        when(passwordEncoder.encode(anyString())).thenReturn("12345678");
        when(passwordEncoder.matches(any(), anyString())).thenReturn(true);
    }

    @Test
    void testLogin() throws Exception {
        UserLoginRequest loginRequest = new UserLoginRequest(user.getUsername(), user.getPassword());
        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/login")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }

    private UserEntity initUser() {
        return new UserEntity(null, "TestUser", "tesusert", "12345678", new ArrayList<>());
    }
}
