package com.dokotech.platform.userservice.controllers;

import com.dokotech.platform.userservice.configs.JwtConfigProperties;
import com.dokotech.platform.userservice.controllers.api.AuthApi;
import com.dokotech.platform.userservice.dtos.AuthenticationResponse;
import com.dokotech.platform.userservice.dtos.LoginRequest;
import com.dokotech.platform.userservice.dtos.RefreshTokenRequest;
import com.dokotech.platform.userservice.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtConfigProperties jwtConfig;
    private final org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    @Override
    public ResponseEntity<AuthenticationResponse> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String accessToken = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(loginRequest.getUsername());

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtConfig.getExpiration())
                .username(loginRequest.getUsername())
                .roles(roles)
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthenticationResponse> refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.getUsernameFromToken(refreshToken);

            // Load user and generate new tokens
            org.springframework.security.core.userdetails.UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            List<String> authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            String newAccessToken = jwtTokenProvider.generateToken(username, authorities);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(username);

            AuthenticationResponse response = AuthenticationResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtConfig.getExpiration())
                    .username(username)
                    .roles(authorities)
                    .build();

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Authentication service is running");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, String>> publicEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "This is a public endpoint - no authentication required");
        response.put("access", "public");
        return ResponseEntity.ok(response);
    }
}

