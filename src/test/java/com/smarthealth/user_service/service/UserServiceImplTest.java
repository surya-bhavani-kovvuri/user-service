package com.smarthealth.user_service.service;

import com.smarthealth.user_service.dto.RegisterUserRequest;
import com.smarthealth.user_service.dto.UserResponse;
import com.smarthealth.user_service.entity.Role;
import com.smarthealth.user_service.entity.User;
import com.smarthealth.user_service.exception.UserAlreadyExistsException;
import com.smarthealth.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // 🧠 STEP 1: Enable Mockito in JUnit 5
public class UserServiceImplTest {

    @Mock // 🧠 STEP 2: Create a fake UserRepository
    UserRepository userRepository;

    @InjectMocks // 🧠 STEP 3: Create real UserServiceImpl and inject fake repo into it
    UserServiceImpl userServiceImpl;

    @Test  // 🧠 STEP 4: This method is a test case
    void testUserRegistration_success(){

        RegisterUserRequest registerUserRequest = new RegisterUserRequest("John","John@hotmail.com","JohnDre",Role.ADMIN);
        User user = new User(1L,"John","John@hotmail.com","JohnDre",Role.ADMIN);

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserResponse registeredUser = userServiceImpl.registerUser(registerUserRequest);

        assertNotNull(registeredUser);
        assertEquals(1L,registeredUser.getId());
        assertEquals("John",registeredUser.getName());
        assertEquals("John@hotmail.com",registeredUser.getEmail());
        assertEquals(Role.ADMIN,registeredUser.getRole());

    }

    @Test
    void testUserRegistration_throwException(){

        RegisterUserRequest registerUserRequest = new RegisterUserRequest("John","John@hotmail.com","JohnDre",Role.ADMIN);
        User user = new User(1L,"John","John@hotmail.com","JohnDre",Role.ADMIN);

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
                () ->userServiceImpl.registerUser(registerUserRequest));


       assertEquals("User already exists with this email :John@hotmail.com",exception.getMessage());

    }
}
