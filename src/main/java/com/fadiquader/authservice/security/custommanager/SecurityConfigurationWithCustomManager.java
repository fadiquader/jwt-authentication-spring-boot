/**
 * This just an example
 */
//package com.fadiquader.authservice.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.http.HttpMethod.POST;
//
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfigurationWithCustomManager {
//    private final AuthenticationManager authenticationManager;
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManager);
//        authenticationFilter.setFilterProcessesUrl("/api/login");
//        http
//                .csrf()
//                .disable()
//                .formLogin()
//                .disable()
//                .httpBasic()
//                .disable()
//                .authorizeRequests()
//                .antMatchers(POST, "/api/login")
//                .permitAll()
//                .antMatchers("*", "/api/user/**")
//                .hasAnyAuthority("ROLE_USER")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .addFilter(authenticationFilter)
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        return http.build();
//    }
//
//}
