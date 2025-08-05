package com.smarthealth.user_service.dto;

import com.smarthealth.user_service.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private Role role;

}
