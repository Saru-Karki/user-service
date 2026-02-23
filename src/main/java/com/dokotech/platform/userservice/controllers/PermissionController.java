package com.dokotech.platform.userservice.controllers;

import com.dokotech.platform.userservice.controllers.api.PermissionApi;
import com.dokotech.platform.userservice.dtos.PermissionCreateDTO;
import com.dokotech.platform.userservice.dtos.PermissionDTO;
import com.dokotech.platform.userservice.dtos.PermissionUpdateDTO;
import com.dokotech.platform.userservice.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PermissionController implements PermissionApi {

    private final PermissionService permissionService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionDTO> createPermission(PermissionCreateDTO createDTO) {
        PermissionDTO created = permissionService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionDTO> getPermissionById(UUID id) {
        PermissionDTO permission = permissionService.getById(id);
        return ResponseEntity.ok(permission);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        List<PermissionDTO> permissions = permissionService.getAll();
        return ResponseEntity.ok(permissions);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionDTO> updatePermission(UUID id, PermissionUpdateDTO updateDTO) {
        PermissionDTO updated = permissionService.update(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePermission(UUID id) {
        permissionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

