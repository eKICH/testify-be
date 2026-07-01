package com.testify.testify.service;

import com.testify.testify.dto.AuthResponse;
import com.testify.testify.dto.LoginRequest;
import com.testify.testify.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(String refreshToken);
}
