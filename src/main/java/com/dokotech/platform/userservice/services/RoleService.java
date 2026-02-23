package com.dokotech.platform.userservice.services;

import com.dokotech.platform.userservice.dtos.RoleCreateDTO;
import com.dokotech.platform.userservice.dtos.RoleDTO;
import com.dokotech.platform.userservice.dtos.RoleUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    RoleDTO create(RoleCreateDTO createDTO);

    RoleDTO getById(UUID id);

    List<RoleDTO> getAll();

    RoleDTO update(UUID id, RoleUpdateDTO updateDTO);

    void delete(UUID id);

    RoleDTO addPermissions(UUID roleId, List<UUID> permissionIds);

    RoleDTO removePermissions(UUID roleId, List<UUID> permissionIds);
}