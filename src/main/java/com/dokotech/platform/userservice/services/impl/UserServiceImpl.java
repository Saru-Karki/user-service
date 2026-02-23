package com.dokotech.platform.userservice.services.impl;

import com.dokotech.platform.userservice.dtos.PermissionDTO;
import com.dokotech.platform.userservice.dtos.RoleDTO;
import com.dokotech.platform.userservice.dtos.UserCreateDTO;
import com.dokotech.platform.userservice.dtos.UserDTO;
import com.dokotech.platform.userservice.dtos.UserUpdateDTO;
import com.dokotech.platform.userservice.exceptions.ResourceAlreadyExistsException;
import com.dokotech.platform.userservice.exceptions.ResourceNotFoundException;
import com.dokotech.platform.userservice.models.Permission;
import com.dokotech.platform.userservice.models.Role;
import com.dokotech.platform.userservice.models.User;
import com.dokotech.platform.userservice.repositories.RoleRepository;
import com.dokotech.platform.userservice.repositories.UserRepository;
import com.dokotech.platform.userservice.services.UserService;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public UserDTO create(UserCreateDTO createDTO) {
        if (userRepository.findByEmail(createDTO.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("User with email '" + createDTO.getEmail() + "' already exists");
        }

        if (userRepository.findByUsername(createDTO.getUsername()).isPresent()) {
            throw new ResourceAlreadyExistsException("User with username '" + createDTO.getUsername() + "' already exists");
        }

        User user = User.builder()
                .email(createDTO.getEmail())
                .username(createDTO.getUsername())
                .passwordHash(passwordEncoder.encode(createDTO.getPassword())) // Hash password with BCrypt
                .fullName(createDTO.getFullName())
                .isActive(createDTO.getIsActive())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .roles(new HashSet<>())
                .build();

        if (createDTO.getRoleIds() != null && !createDTO.getRoleIds().isEmpty()) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(createDTO.getRoleIds()));
            user.setRoles(roles);
        }

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return mapToDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return mapToDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO update(UUID id, UserUpdateDTO updateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (updateDTO.getEmail() != null) {
            userRepository.findByEmail(updateDTO.getEmail()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new ResourceAlreadyExistsException("User with email '" + updateDTO.getEmail() + "' already exists");
                }
            });
            user.setEmail(updateDTO.getEmail());
        }

        if (updateDTO.getUsername() != null) {
            userRepository.findByUsername(updateDTO.getUsername()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new ResourceAlreadyExistsException("User with username '" + updateDTO.getUsername() + "' already exists");
                }
            });
            user.setUsername(updateDTO.getUsername());
        }

        if (updateDTO.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(updateDTO.getPassword())); // Hash password with BCrypt
        }

        if (updateDTO.getFullName() != null) {
            user.setFullName(updateDTO.getFullName());
        }

        if (updateDTO.getIsActive() != null) {
            user.setIsActive(updateDTO.getIsActive());
        }

        if (updateDTO.getRoleIds() != null) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(updateDTO.getRoleIds()));
            user.setRoles(roles);
        }

        user.setUpdatedAt(Instant.now());

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Override
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO addRoles(UUID userId, List<UUID> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<Role> roles = roleRepository.findAllById(roleIds);
        user.getRoles().addAll(roles);
        user.setUpdatedAt(Instant.now());

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Override
    public UserDTO removeRoles(UUID userId, List<UUID> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<Role> roles = roleRepository.findAllById(roleIds);
        user.getRoles().removeAll(roles);
        user.setUpdatedAt(Instant.now());

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Override
    public UserDTO activate(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setIsActive(true);
        user.setUpdatedAt(Instant.now());

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Override
    public UserDTO deactivate(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setIsActive(false);
        user.setUpdatedAt(Instant.now());

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .roles(user.getRoles().stream()
                        .map(this::mapRoleToDTO)
                        .collect(Collectors.toSet()))
                .build();

        if (user.getCreatedBy() != null) {
            dto.setCreatedById(user.getCreatedBy().getId());
            dto.setCreatedByUsername(user.getCreatedBy().getUsername());
        }

        return dto;
    }

    private RoleDTO mapRoleToDTO(Role role) {
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

