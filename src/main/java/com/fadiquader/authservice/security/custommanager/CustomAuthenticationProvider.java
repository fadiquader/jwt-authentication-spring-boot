/**
 * This just an example
 */
//package com.fadiquader.authservice.security;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
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
//// https://github.com/eazybytes/spring-security/blob/dcff12fc0af63d494e54ff0b3eaa731781bdb891/section_10/springsecsection10latest/src/main/java/com/eazybytes/config/EazyBankUsernamePwdAuthenticationProvider.java
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class CustomAuthenticationProvider implements AuthenticationProvider {
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
//        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authenticationType) {
//        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}
