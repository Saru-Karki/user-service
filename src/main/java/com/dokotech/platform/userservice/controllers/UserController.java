package com.dokotech.platform.userservice.controllers;

import com.dokotech.platform.userservice.controllers.api.UserApi;
import com.dokotech.platform.userservice.dtos.UserCreateDTO;
import com.dokotech.platform.userservice.dtos.UserDTO;
import com.dokotech.platform.userservice.dtos.UserUpdateDTO;
import com.dokotech.platform.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDTO> createUser(UserCreateDTO createDTO) {
        UserDTO created = userService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(UUID id) {
        UserDTO user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserByUsername(String username) {
        UserDTO user = userService.getByUsername(username);
        return ResponseEntity.ok(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserByEmail(String email) {
        UserDTO user = userService.getByEmail(email);
        return ResponseEntity.ok(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(UUID id, UserUpdateDTO updateDTO) {
        UserDTO updated = userService.update(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> addRoles(UUID id, List<UUID> roleIds) {
        UserDTO updated = userService.addRoles(id, roleIds);
        return ResponseEntity.ok(updated);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> removeRoles(UUID id, List<UUID> roleIds) {
        UserDTO updated = userService.removeRoles(id, roleIds);
        return ResponseEntity.ok(updated);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> activateUser(UUID id) {
        UserDTO activated = userService.activate(id);
        return ResponseEntity.ok(activated);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> deactivateUser(UUID id) {
        UserDTO deactivated = userService.deactivate(id);
        return ResponseEntity.ok(deactivated);
    }
}

