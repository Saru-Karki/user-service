package com.dokotech.platform.userservice.dtos;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionUpdateDTO {

    @Size(min = 3, max = 100, message = "Permission name must be between 3 and 100 characters")
    private String name;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
}

