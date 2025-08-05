package com.smarthealth.user_service.controller;

import com.smarthealth.user_service.dto.RegisterUserRequest;
import com.smarthealth.user_service.dto.UserResponse;
import com.smarthealth.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse registerUser(@Valid
                                         @RequestBody RegisterUserRequest registerUserRequest){
        return userService.registerUser(registerUserRequest);
    }

    @GetMapping("")
    public UserResponse getUserById(@RequestParam Long id){
        return userService.getUserById(id);
    }
}
