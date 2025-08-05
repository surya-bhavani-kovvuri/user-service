package com.smarthealth.user_service.mapper;

import com.smarthealth.user_service.dto.RegisterUserRequest;
import com.smarthealth.user_service.dto.UserResponse;
import com.smarthealth.user_service.entity.User;


public class UserMapper {
    public static UserResponse toUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setRole(user.getRole());
        response.setId(user.getId());
        return response;

    }

    public static User toUserDataFromUserRequest(RegisterUserRequest registerUserRequest){
        User user = new User();
        user.setName(registerUserRequest.getName());
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(registerUserRequest.getPassword());
        user.setRole(registerUserRequest.getRole());
        return user;
    }
}
