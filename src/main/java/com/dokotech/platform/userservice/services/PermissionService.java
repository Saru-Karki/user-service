package com.dokotech.platform.userservice.services;

import com.dokotech.platform.userservice.dtos.PermissionCreateDTO;
import com.dokotech.platform.userservice.dtos.PermissionDTO;
import com.dokotech.platform.userservice.dtos.PermissionUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

    PermissionDTO create(PermissionCreateDTO createDTO);

    PermissionDTO getById(UUID id);

    List<PermissionDTO> getAll();

    PermissionDTO update(UUID id, PermissionUpdateDTO updateDTO);

    void delete(UUID id);
}
