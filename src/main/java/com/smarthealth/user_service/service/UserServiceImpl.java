package com.smarthealth.user_service.service;

import com.smarthealth.user_service.dto.RegisterUserRequest;
import com.smarthealth.user_service.dto.UpdateUserRequest;
import com.smarthealth.user_service.dto.UserResponse;
import com.smarthealth.user_service.entity.User;
import com.smarthealth.user_service.exception.UserAlreadyExistsException;
import com.smarthealth.user_service.exception.UserNotFoundException;
import com.smarthealth.user_service.mapper.UserMapper;
import com.smarthealth.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new UserNotFoundException("No user found for the given id: "+id));
        return UserMapper.toUserResponse(userDetails);
    }

    public List<UserResponse> getAllUsers(){
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserMapper::toUserResponse)
                .toList();
    }

    public UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found with the given id"));

        if (updateUserRequest.getName() != null) {
            user.setName(updateUserRequest.getName());
        }
        if (updateUserRequest.getPassword() != null) {
            user.setPassword(updateUserRequest.getPassword());
        }
        if (updateUserRequest.getRole() != null) {
            user.setRole(updateUserRequest.getRole());
        }

        User updatedUser = userRepository.save(user);
        return UserMapper.toUserResponse(updatedUser);
    }

    public void deleteUserById(Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException("No user found with the given id: "+id);
        }
        userRepository.deleteById(id);
    }

}
