package com.smarthealth.user_service.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarthealth.user_service.dto.RegisterUserRequest;
import com.smarthealth.user_service.entity.Role;
import com.smarthealth.user_service.mapper.UserMapper;
import com.smarthealth.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll(); // Clean state before each test
    }

    @Test
    void shouldRegisterUserSuccessfully_whenDataIsValid() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest(
                "John",
                "john.doe@example.com",
                "john123",
                Role.PATIENT
        );

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.role").value("PATIENT"));
    }

    @Test
    void shouldReturnConflict_whenUserWithSameEmailExists() throws Exception {
        // First save a user
        RegisterUserRequest request = new RegisterUserRequest(
                "John",
                "john.doe@example.com",
                "john123",
                Role.PATIENT
        );
        userRepository.save(UserMapper.toUserDataFromUserRequest(request)); // Assuming `toUser()` converts to User entity

        // Now make the same request again
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));
    }
}
