package com.dokotech.platform.userservice.controllers.api;

import com.dokotech.platform.userservice.dtos.PermissionCreateDTO;
import com.dokotech.platform.userservice.dtos.PermissionDTO;
import com.dokotech.platform.userservice.dtos.PermissionUpdateDTO;
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

@Tag(name = "Permission Management", description = "APIs for managing permissions in the system")
@RequestMapping("/api/v1/permissions")
public interface PermissionApi {

    @Operation(summary = "Create a new permission", description = "Creates a new permission with the provided details")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Permission created successfully", content = @Content(schema = @Schema(implementation = PermissionDTO.class))), @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "409", description = "Permission with the same name already exists", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping
    ResponseEntity<PermissionDTO> createPermission(@Valid @RequestBody PermissionCreateDTO createDTO);

    @Operation(summary = "Get permission by ID", description = "Retrieves a permission by its unique identifier")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Permission found", content = @Content(schema = @Schema(implementation = PermissionDTO.class))), @ApiResponse(responseCode = "404", description = "Permission not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}")
    ResponseEntity<PermissionDTO> getPermissionById(@Parameter(description = "Permission ID", required = true) @PathVariable UUID id);

    @Operation(summary = "Get all permissions", description = "Retrieves a list of all permissions in the system")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Permissions retrieved successfully", content = @Content(schema = @Schema(implementation = PermissionDTO.class)))})
    @GetMapping
    ResponseEntity<List<PermissionDTO>> getAllPermissions();

    @Operation(summary = "Update permission", description = "Updates an existing permission with the provided details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Permission updated successfully", content = @Content(schema = @Schema(implementation = PermissionDTO.class))), @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "404", description = "Permission not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "409", description = "Permission with the same name already exists", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping("/{id}")
    ResponseEntity<PermissionDTO> updatePermission(@Parameter(description = "Permission ID", required = true) @PathVariable UUID id, @Valid @RequestBody PermissionUpdateDTO updateDTO);

    @Operation(summary = "Delete permission", description = "Deletes a permission from the system")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Permission deleted successfully"), @ApiResponse(responseCode = "404", description = "Permission not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePermission(@Parameter(description = "Permission ID", required = true) @PathVariable UUID id);
}

