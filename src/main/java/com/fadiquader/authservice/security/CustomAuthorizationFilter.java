package com.fadiquader.authservice.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fadiquader.authservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String path = request.getServletPath();
        String token = parseJwt(request);
        log.info("hola {}", token);
        DecodedJWT decodedJWT = jwtUtils.verifyAccessToken(token);

        if (decodedJWT != null) {
            String username = decodedJWT.getSubject();
            UserDetails userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            try {
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception ex) {
                log.error("Cannot set user authentication: {}", ex.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
    private String parseJwt(HttpServletRequest request) {
        String HEADER_STRING = "Authorization";
        String authHeader = request.getHeader(HEADER_STRING);
        String TOKEN_PREFIX = "Bearer ";
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) return null;

        return authHeader.replace(TOKEN_PREFIX, "");
    }
}
