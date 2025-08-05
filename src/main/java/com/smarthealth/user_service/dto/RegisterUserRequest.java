package com.smarthealth.user_service.dto;

import com.smarthealth.user_service.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {

    @NotBlank(message = "Name can't be null")
    private String name;

    @Email(message = "Invalid email id")
    @NotBlank(message = " Email can't be null")
    private String email;

    @NotBlank(message = "Invalid format")
    private String password;

    private Role role;


}
