package com.dokotech.platform.userservice.services;

import com.dokotech.platform.userservice.dtos.UserCreateDTO;
import com.dokotech.platform.userservice.dtos.UserDTO;
import com.dokotech.platform.userservice.dtos.UserUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTO create(UserCreateDTO createDTO);

    UserDTO getById(UUID id);

    UserDTO getByUsername(String username);

    UserDTO getByEmail(String email);

    List<UserDTO> getAll();

    UserDTO update(UUID id, UserUpdateDTO updateDTO);

    void delete(UUID id);

    UserDTO addRoles(UUID userId, List<UUID> roleIds);

    UserDTO removeRoles(UUID userId, List<UUID> roleIds);

    UserDTO activate(UUID id);

    UserDTO deactivate(UUID id);
}


