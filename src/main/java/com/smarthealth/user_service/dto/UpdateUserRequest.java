package com.smarthealth.user_service.dto;

import com.smarthealth.user_service.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    private String name;
    private String password;
    private Role role;
}
