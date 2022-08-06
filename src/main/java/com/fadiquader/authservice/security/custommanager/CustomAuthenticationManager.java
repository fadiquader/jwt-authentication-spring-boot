/**
 * This just an example
 */
//package com.fadiquader.authservice.security;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//// https://stackoverflow.com/questions/65659155/class-org-springframework-security-core-userdetails-user-cannot-be-cast
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class CustomAuthenticationManager implements AuthenticationManager {
//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        UserDetails user = userDetailsService.loadUserByUsername(username);
//        String password = authentication.getCredentials().toString();
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new BadCredentialsException("Invalid username or password");
//        }
//
//        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//    }
//}
