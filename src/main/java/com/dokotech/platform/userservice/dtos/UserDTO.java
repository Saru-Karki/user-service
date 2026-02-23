package com.dokotech.platform.userservice.dtos;

import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private UUID id;
    private String email;
    private String username;
    private String fullName;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID createdById;
    private String createdByUsername;
    @Builder.Default
    private Set<RoleDTO> roles = new HashSet<>();
}

