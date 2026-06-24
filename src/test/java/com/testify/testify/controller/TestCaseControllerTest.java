package com.testify.testify.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testify.testify.dto.TestCaseCreateRequest;
import com.testify.testify.entity.Priority;
import com.testify.testify.entity.User;
import com.testify.testify.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TestCaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setEmail("user@test.com");
        user.setPassword("password");
        userRepository.save(user);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testCreateTestCaseEndpoint() throws Exception {
        TestCaseCreateRequest request = new TestCaseCreateRequest();
        request.setName("Test Case");
        request.setPriority(Priority.HIGH);

        mockMvc.perform(post("/api/v1/test-cases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
