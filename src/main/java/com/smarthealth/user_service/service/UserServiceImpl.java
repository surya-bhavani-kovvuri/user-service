package com.smarthealth.user_service.service;

import com.smarthealth.user_service.dto.RegisterUserRequest;
import com.smarthealth.user_service.dto.UserResponse;
import com.smarthealth.user_service.entity.User;
import com.smarthealth.user_service.exception.UserAlreadyExistsException;
import com.smarthealth.user_service.exception.UserNotFoundException;
import com.smarthealth.user_service.mapper.UserMapper;
import com.smarthealth.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse registerUser(RegisterUserRequest registerUserRequest){
        Optional<User> existingUser = userRepository.findByEmail(registerUserRequest.getEmail());
        if(existingUser.isPresent()){
            throw new UserAlreadyExistsException("User already exists with this email :"+registerUserRequest.getEmail());
        }
        User savedUser = userRepository.save(UserMapper.toUserDataFromUserRequest(registerUserRequest));
        return UserMapper.toUserResponse(savedUser);
    }
    @Override
    public UserResponse getUserById(Long id) {
        User userDetails = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found for the given id :"+id));
        return UserMapper.toUserResponse(userDetails);
    }
}
