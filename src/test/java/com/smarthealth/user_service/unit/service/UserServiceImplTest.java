package com.smarthealth.user_service.unit.service;

import com.smarthealth.user_service.dto.RegisterUserRequest;
import com.smarthealth.user_service.dto.UserResponse;
import com.smarthealth.user_service.entity.Role;
import com.smarthealth.user_service.entity.User;
import com.smarthealth.user_service.exception.UserAlreadyExistsException;
import com.smarthealth.user_service.exception.UserNotFoundException;
import com.smarthealth.user_service.repository.UserRepository;
import com.smarthealth.user_service.service.UserServiceImpl;
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

    @Test
    void testGettingUserDetails_success(){
        Long id = 1L;
        User user = new User(1L,"Surya","surya@gmail.com","surya123",Role.DOCTOR);

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        UserResponse userDetails = userServiceImpl.getUserById(id);

        assertNotNull(userDetails);
        assertEquals(1L,userDetails.getId());
        assertEquals("Surya",userDetails.getName());
        assertEquals("surya@gmail.com",userDetails.getEmail());
        assertEquals(Role.DOCTOR,userDetails.getRole());

    }

    @Test
    void testUserNotFoundException(){
        Long id = 2L;

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> userServiceImpl.getUserById(id));

        assertEquals("No user found for the given id: 2",ex.getMessage());
    }

}
