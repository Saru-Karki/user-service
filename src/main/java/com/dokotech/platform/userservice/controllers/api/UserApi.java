package com.dokotech.platform.userservice.controllers.api;

import com.dokotech.platform.userservice.dtos.UserCreateDTO;
import com.dokotech.platform.userservice.dtos.UserDTO;
import com.dokotech.platform.userservice.dtos.UserUpdateDTO;
import com.dokotech.platform.userservice.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "User Management", description = "APIs for managing users, their roles, and account status")
@RequestMapping("/api/v1/users")
public interface UserApi {

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the provided details and optional roles"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User with the same email or username already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    ResponseEntity<UserDTO> createUser(
            @Valid @RequestBody UserCreateDTO createDTO
    );

    @Operation(
            summary = "Get user by ID",
            description = "Retrieves a user with all their roles and permissions by unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "User ID", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Get user by username",
            description = "Retrieves a user by their username"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/username/{username}")
    ResponseEntity<UserDTO> getUserByUsername(
            @Parameter(description = "Username", required = true)
            @PathVariable String username
    );

    @Operation(
            summary = "Get user by email",
            description = "Retrieves a user by their email address"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/email/{email}")
    ResponseEntity<UserDTO> getUserByEmail(
            @Parameter(description = "Email address", required = true)
            @PathVariable String email
    );

    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all users in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Users retrieved successfully",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            )
    })
    @GetMapping
    ResponseEntity<List<UserDTO>> getAllUsers();

    @Operation(
            summary = "Update user",
            description = "Updates an existing user with the provided details"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated successfully",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User with the same email or username already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping("/{id}")
    ResponseEntity<UserDTO> updateUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateDTO updateDTO
    );

    @Operation(
            summary = "Delete user",
            description = "Deletes a user from the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Add roles to user",
            description = "Adds one or more roles to an existing user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Roles added successfully",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/{id}/roles")
    ResponseEntity<UserDTO> addRoles(
            @Parameter(description = "User ID", required = true)
            @PathVariable UUID id,
            @RequestBody List<UUID> roleIds
    );

    @Operation(
            summary = "Remove roles from user",
            description = "Removes one or more roles from an existing user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Roles removed successfully",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{id}/roles")
    ResponseEntity<UserDTO> removeRoles(
            @Parameter(description = "User ID", required = true)
            @PathVariable UUID id,
            @RequestBody List<UUID> roleIds
    );

    @Operation(
            summary = "Activate user",
            description = "Activates a user account"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User activated successfully",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/{id}/activate")
    ResponseEntity<UserDTO> activateUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Deactivate user",
            description = "Deactivates a user account"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User deactivated successfully",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/{id}/deactivate")
    ResponseEntity<UserDTO> deactivateUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable UUID id
    );
}

