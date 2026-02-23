
package com.dokotech.platform.userservice.controllers;

import com.dokotech.platform.userservice.controllers.api.RoleApi;
import com.dokotech.platform.userservice.dtos.RoleCreateDTO;
import com.dokotech.platform.userservice.dtos.RoleDTO;
import com.dokotech.platform.userservice.dtos.RoleUpdateDTO;
import com.dokotech.platform.userservice.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RoleController implements RoleApi {

    private final RoleService roleService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDTO> createRole(RoleCreateDTO createDTO) {
        RoleDTO created = roleService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDTO> getRoleById(UUID id) {
        RoleDTO role = roleService.getById(id);
        return ResponseEntity.ok(role);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAll();
        return ResponseEntity.ok(roles);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDTO> updateRole(UUID id, RoleUpdateDTO updateDTO) {
        RoleDTO updated = roleService.update(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRole(UUID id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDTO> addPermissions(UUID id, List<UUID> permissionIds) {
        RoleDTO updated = roleService.addPermissions(id, permissionIds);
        return ResponseEntity.ok(updated);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDTO> removePermissions(UUID id, List<UUID> permissionIds) {
        RoleDTO updated = roleService.removePermissions(id, permissionIds);
        return ResponseEntity.ok(updated);
    }
}