package com.smarthealth.user_service.service;

import com.smarthealth.user_service.dto.RegisterUserRequest;
import com.smarthealth.user_service.dto.UpdateUserRequest;
import com.smarthealth.user_service.dto.UserResponse;

import java.util.List;

 public interface UserService {

     UserResponse registerUser(RegisterUserRequest registerUserRequest);

     UserResponse getUserById(Long id);

     List<UserResponse> getAllUsers();

     UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest);

     void deleteUserById(Long id);
}
