
package com.dokotech.platform.userservice.controllers.api;

import com.dokotech.platform.userservice.dtos.RoleCreateDTO;
import com.dokotech.platform.userservice.dtos.RoleDTO;
import com.dokotech.platform.userservice.dtos.RoleUpdateDTO;
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

@Tag(name = "Role Management", description = "APIs for managing roles and their permissions")
@RequestMapping("/api/v1/roles")
public interface RoleApi {

    @Operation(
            summary = "Create a new role",
            description = "Creates a new role with optional permissions"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Role created successfully",
                    content = @Content(schema = @Schema(implementation = RoleDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Role with the same name already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    ResponseEntity<RoleDTO> createRole(
            @Valid @RequestBody RoleCreateDTO createDTO
    );

    @Operation(
            summary = "Get role by ID",
            description = "Retrieves a role with all its permissions by its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Role found",
                    content = @Content(schema = @Schema(implementation = RoleDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Role not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    ResponseEntity<RoleDTO> getRoleById(
            @Parameter(description = "Role ID", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Get all roles",
            description = "Retrieves a list of all roles with their permissions"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Roles retrieved successfully",
                    content = @Content(schema = @Schema(implementation = RoleDTO.class))
            )
    })
    @GetMapping
    ResponseEntity<List<RoleDTO>> getAllRoles();

    @Operation(
            summary = "Update role",
            description = "Updates an existing role and optionally its permissions"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Role updated successfully",
                    content = @Content(schema = @Schema(implementation = RoleDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Role not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Role with the same name already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping("/{id}")
    ResponseEntity<RoleDTO> updateRole(
            @Parameter(description = "Role ID", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody RoleUpdateDTO updateDTO
    );

    @Operation(
            summary = "Delete role",
            description = "Deletes a role from the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Role deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Role not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteRole(
            @Parameter(description = "Role ID", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Add permissions to role",
            description = "Adds one or more permissions to an existing role"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Permissions added successfully",
                    content = @Content(schema = @Schema(implementation = RoleDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Role not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/{id}/permissions")
    ResponseEntity<RoleDTO> addPermissions(
            @Parameter(description = "Role ID", required = true)
            @PathVariable UUID id,
            @RequestBody List<UUID> permissionIds
    );

    @Operation(
            summary = "Remove permissions from role",
            description = "Removes one or more permissions from an existing role"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Permissions removed successfully",
                    content = @Content(schema = @Schema(implementation = RoleDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Role not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{id}/permissions")
    ResponseEntity<RoleDTO> removePermissions(
            @Parameter(description = "Role ID", required = true)
            @PathVariable UUID id,
            @RequestBody List<UUID> permissionIds
    );
}