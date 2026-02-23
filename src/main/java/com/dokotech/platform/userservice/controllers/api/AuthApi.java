package com.dokotech.platform.userservice.controllers.api;

import com.dokotech.platform.userservice.dtos.AuthenticationResponse;
import com.dokotech.platform.userservice.dtos.LoginRequest;
import com.dokotech.platform.userservice.dtos.RefreshTokenRequest;
import com.dokotech.platform.userservice.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Authentication", description = "Authentication and authorization endpoints for user login, token refresh, and health checks")
@RequestMapping("/api/auth")
public interface AuthApi {

    @Operation(
            summary = "User Login",
            description = "Authenticate user with username and password, returns JWT access token and refresh token"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful, JWT tokens returned",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request - missing or invalid credentials",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication failed - invalid username or password",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody LoginRequest loginRequest
    );

    @Operation(
            summary = "Refresh Access Token",
            description = "Generate a new access token using a valid refresh token. Both access and refresh tokens are returned."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Token refreshed successfully",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid or expired refresh token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Refresh token validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/refresh")
    ResponseEntity<AuthenticationResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    );

    @Operation(
            summary = "Health Check",
            description = "Check if the authentication service is running and operational"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Service is healthy and running",
                    content = @Content(schema = @Schema(implementation = Map.class))
            )
    })
    @GetMapping("/health")
    ResponseEntity<Map<String, String>> health();

    @Operation(
            summary = "Public Endpoint",
            description = "Test public endpoint that doesn't require authentication. Used for testing API accessibility."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Public endpoint accessed successfully",
                    content = @Content(schema = @Schema(implementation = Map.class))
            )
    })
    @GetMapping("/public")
    ResponseEntity<Map<String, String>> publicEndpoint();
}

