package com.fadiquader.authservice.service.impl;

import com.fadiquader.authservice.domain.entity.RoleEntity;
import com.fadiquader.authservice.domain.entity.UserEntity;
import com.fadiquader.authservice.repository.RoleRepository;
import com.fadiquader.authservice.repository.UserRepository;
import com.fadiquader.authservice.security.UserPrincipal;
import com.fadiquader.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        log.info("User found {}", user.getUsername());

        return new UserPrincipal(user);
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        log.info("Saving new user {} in the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public RoleEntity saveRole(RoleEntity role) {
        log.info("Saving new role {} in the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        RoleEntity role = roleRepository.findByName(roleName);
        user.getRoles().add(role);

    }

    @Override
    public UserEntity getUser(String username) {
        log.info("fetching user {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public List<UserEntity> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }
}
