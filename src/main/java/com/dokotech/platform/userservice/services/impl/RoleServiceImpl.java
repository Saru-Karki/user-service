package com.dokotech.platform.userservice.services.impl;

import com.dokotech.platform.userservice.dtos.PermissionDTO;
import com.dokotech.platform.userservice.dtos.RoleCreateDTO;
import com.dokotech.platform.userservice.dtos.RoleDTO;
import com.dokotech.platform.userservice.dtos.RoleUpdateDTO;
import com.dokotech.platform.userservice.exceptions.ResourceAlreadyExistsException;
import com.dokotech.platform.userservice.exceptions.ResourceNotFoundException;
import com.dokotech.platform.userservice.models.Permission;
import com.dokotech.platform.userservice.models.Role;
import com.dokotech.platform.userservice.repositories.PermissionRepository;
import com.dokotech.platform.userservice.repositories.RoleRepository;
import com.dokotech.platform.userservice.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public RoleDTO create(RoleCreateDTO createDTO) {
        if (roleRepository.findByName(createDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Role with name '" + createDTO.getName() + "' already exists");
        }

        Role role = Role.builder()
                .name(createDTO.getName())
                .description(createDTO.getDescription())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .permissions(new HashSet<>())
                .build();

        if (createDTO.getPermissionIds() != null && !createDTO.getPermissionIds().isEmpty()) {
            Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(createDTO.getPermissionIds()));
            role.setPermissions(permissions);
        }

        Role savedRole = roleRepository.save(role);
        return mapToDTO(savedRole);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDTO getById(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return mapToDTO(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> getAll() {
        return roleRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO update(UUID id, RoleUpdateDTO updateDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        if (updateDTO.getName() != null) {
            roleRepository.findByName(updateDTO.getName()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new ResourceAlreadyExistsException("Role with name '" + updateDTO.getName() + "' already exists");
                }
            });
            role.setName(updateDTO.getName());
        }

        if (updateDTO.getDescription() != null) {
            role.setDescription(updateDTO.getDescription());
        }

        if (updateDTO.getPermissionIds() != null) {
            Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(updateDTO.getPermissionIds()));
            role.setPermissions(permissions);
        }

        role.setUpdatedAt(Instant.now());

        Role updatedRole = roleRepository.save(role);
        return mapToDTO(updatedRole);
    }

    @Override
    public void delete(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDTO addPermissions(UUID roleId, List<UUID> permissionIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));

        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        role.getPermissions().addAll(permissions);
        role.setUpdatedAt(Instant.now());

        Role updatedRole = roleRepository.save(role);
        return mapToDTO(updatedRole);
    }

    @Override
    public RoleDTO removePermissions(UUID roleId, List<UUID> permissionIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));

        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        role.getPermissions().removeAll(permissions);
        role.setUpdatedAt(Instant.now());

        Role updatedRole = roleRepository.save(role);
        return mapToDTO(updatedRole);
    }

    private RoleDTO mapToDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .permissions(role.getPermissions().stream()
                        .map(this::mapPermissionToDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

    private PermissionDTO mapPermissionToDTO(Permission permission) {
        return PermissionDTO.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .createdAt(permission.getCreatedAt())
                .updatedAt(permission.getUpdatedAt())
                .build();
    }
}

