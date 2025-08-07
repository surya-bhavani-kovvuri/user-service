package com.smarthealth.user_service.controller;

import com.smarthealth.user_service.dto.RegisterUserRequest;
import com.smarthealth.user_service.dto.UpdateUserRequest;
import com.smarthealth.user_service.dto.UserResponse;
import com.smarthealth.user_service.entity.User;
import com.smarthealth.user_service.payload.ApiResponse;
import com.smarthealth.user_service.service.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LogManager.getLogger(UserController.class);


    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid
                                         @RequestBody RegisterUserRequest registerUserRequest){
        UserResponse response = userService.registerUser(registerUserRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/user/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        ApiResponse<UserResponse> body = new ApiResponse<>("User registration successful",response);
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id){
        UserResponse response = userService.getUserById(id);
        ApiResponse<UserResponse> body = new ApiResponse<>("Fetched user details successfully",response);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
        logger.info("Initialized request to fetch the details of all the users");
        List<UserResponse> userDetailsList = userService.getAllUsers();
        logger.info("Fetching all users — total found: {}", userDetailsList.size());
        ApiResponse<List<UserResponse>> body = new ApiResponse<>("User details fetched successfully",userDetailsList);
        return ResponseEntity.ok(body);

    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserDetails(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest updateUserRequest) {

        UserResponse updatedUser = userService.updateUser(id, updateUserRequest);
        return ResponseEntity.ok(new ApiResponse<>("User updated successfully", updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        ApiResponse body = new ApiResponse("Successfully deleted the user with id: "+id, LocalDateTime.now());
        return ResponseEntity.ok(body);
    }

}
