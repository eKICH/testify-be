package com.testify.testify.service;

import com.testify.testify.dto.TestCaseCreateRequest;
import com.testify.testify.dto.TestCaseResponse;
import com.testify.testify.entity.TestCase;
import com.testify.testify.entity.User;
import com.testify.testify.mapper.TestCaseMapper;
import com.testify.testify.repository.TestCaseRepository;
import com.testify.testify.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestCaseServiceTest {

    @Mock
    private TestCaseRepository testCaseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TestCaseMapper testCaseMapper;

    @InjectMocks
    private TestCaseServiceImpl testCaseService;

    @Test
    void testCreateTestCase() {
        TestCaseCreateRequest request = new TestCaseCreateRequest();
        request.setName("Test Case 1");

        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(testCaseMapper.toTestCase(any(TestCaseCreateRequest.class))).thenReturn(new TestCase());
        when(testCaseRepository.save(any())).thenReturn(new TestCase());
        when(testCaseMapper.toTestCaseResponse(any())).thenReturn(new TestCaseResponse());

        TestCaseResponse response = testCaseService.createTestCase(request, 1L);

        assertNotNull(response);
    }
}
