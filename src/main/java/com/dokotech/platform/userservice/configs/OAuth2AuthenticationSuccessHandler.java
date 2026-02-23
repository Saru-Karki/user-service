package com.dokotech.platform.userservice.configs;

import com.dokotech.platform.userservice.models.User;
import com.dokotech.platform.userservice.repositories.UserRepository;
import com.dokotech.platform.userservice.utils.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${oauth2.frontend-redirect-url}")
    private String frontendRedirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Extract user info from OAuth2 provider
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String provider = extractProvider(request);

        log.info("OAuth2 login successful for email: {} via provider: {}", email, provider);

        // Find or create user
        User user = userRepository.findByEmail(email)
            .orElseGet(() -> createNewOAuth2User(email, name, provider));

        // Generate JWT token with user authorities
        String token = jwtTokenProvider.generateToken(user.getUsername(), Collections.emptyList());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername());

        // Redirect to frontend with token
        String redirectUrl = UriComponentsBuilder.fromUriString(frontendRedirectUrl)
            .queryParam("token", token)
            .queryParam("refreshToken", refreshToken)
            .queryParam("provider", provider)
            .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private User createNewOAuth2User(String email, String name, String provider) {
        log.info("Creating new user from OAuth2 provider: {} for email: {}", provider, email);

        // Generate username from email or name
        String username = email.split("@")[0] + "_" + provider.toLowerCase();

        // Check if username exists and make it unique
        int counter = 1;
        String originalUsername = username;
        while (userRepository.findByUsername(username).isPresent()) {
            username = originalUsername + counter++;
        }

        User user = User.builder()
            .email(email)
            .username(username)
            .fullName(name != null ? name : email)
            .passwordHash(passwordEncoder.encode(UUID.randomUUID().toString())) // Random password for OAuth users
            .provider(provider)
            .isActive(true)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .roles(new HashSet<>())
            .build();

        return userRepository.save(user);
    }

    private String extractProvider(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        if (requestUri.contains("google")) {
            return "GOOGLE";
        } else if (requestUri.contains("facebook")) {
            return "FACEBOOK";
        } else if (requestUri.contains("apple")) {
            return "APPLE";
        }
        return "UNKNOWN";
    }
}

