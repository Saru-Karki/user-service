
package com.dokotech.platform.userservice.services.impl;

import com.dokotech.platform.userservice.dtos.PermissionCreateDTO;
import com.dokotech.platform.userservice.dtos.PermissionDTO;
import com.dokotech.platform.userservice.dtos.PermissionUpdateDTO;
import com.dokotech.platform.userservice.exceptions.ResourceAlreadyExistsException;
import com.dokotech.platform.userservice.exceptions.ResourceNotFoundException;
import com.dokotech.platform.userservice.models.Permission;
import com.dokotech.platform.userservice.repositories.PermissionRepository;
import com.dokotech.platform.userservice.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public PermissionDTO create(PermissionCreateDTO createDTO) {
        if (permissionRepository.findByName(createDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Permission with name '" + createDTO.getName() + "' already exists");
        }

        Permission permission = Permission.builder()
                .name(createDTO.getName())
                .description(createDTO.getDescription())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Permission savedPermission = permissionRepository.save(permission);
        return mapToDTO(savedPermission);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionDTO getById(UUID id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
        return mapToDTO(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO> getAll() {
        return permissionRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PermissionDTO update(UUID id, PermissionUpdateDTO updateDTO) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));

        if (updateDTO.getName() != null) {
            permissionRepository.findByName(updateDTO.getName()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new ResourceAlreadyExistsException("Permission with name '" + updateDTO.getName() + "' already exists");
                }
            });
            permission.setName(updateDTO.getName());
        }

        if (updateDTO.getDescription() != null) {
            permission.setDescription(updateDTO.getDescription());
        }

        permission.setUpdatedAt(Instant.now());

        Permission updatedPermission = permissionRepository.save(permission);
        return mapToDTO(updatedPermission);
    }

    @Override
    public void delete(UUID id) {
        if (!permissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Permission not found with id: " + id);
        }
        permissionRepository.deleteById(id);
    }

    private PermissionDTO mapToDTO(Permission permission) {
        return PermissionDTO.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .createdAt(permission.getCreatedAt())
                .updatedAt(permission.getUpdatedAt())
                .build();
    }
}