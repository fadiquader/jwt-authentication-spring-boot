package com.fadiquader.authservice.service;

import com.fadiquader.authservice.domain.entity.RoleEntity;
import com.fadiquader.authservice.domain.entity.UserEntity;
import com.fadiquader.authservice.repository.RoleRepository;
import com.fadiquader.authservice.repository.UserRepository;
import com.fadiquader.authservice.security.UserPrincipal;
import com.fadiquader.authservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestUserServiceImpl {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    private UserEntity user;
    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setName("User");
        user.setPassword("12345678");
        user.setUsername("test_user");
    }

    @Test
    void whenUserNotFound_thenThrowUsernameNotFoundException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(
                UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("Hola"),
                "Hola was not found"
        );
    }

    @Test
    void loadByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        UserDetails userDetails = (UserPrincipal) userService.loadUserByUsername(anyString());
        assertNotNull(userDetails.getUsername());
        assertNotNull(userDetails.getUsername());
    }

    @Test
    void saveUser() {
        when(passwordEncoder.encode(anyString())).thenReturn("87654321");
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        UserEntity savedUser = userService.saveUser(user);
        assertNotNull(savedUser);
        assertEquals(savedUser.getPassword(), "87654321");
        assertEquals(savedUser.getUsername(), "test_user");
    }

    @Test
    void addRoleToUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        RoleEntity role = new RoleEntity();
        role.setName("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(role);
        userService.addRoleToUser(user.getUsername(), role.getName());
        assertEquals(user.getRoles().stream().findFirst().get().getName(), "ROLE_USER");
    }
}
