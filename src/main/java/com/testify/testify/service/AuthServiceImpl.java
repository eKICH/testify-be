package com.testify.testify.service;

import com.testify.testify.dto.AuthResponse;
import com.testify.testify.dto.LoginRequest;
import com.testify.testify.dto.RegisterRequest;
import com.testify.testify.dto.UserDto;
import com.testify.testify.entity.User;
import com.testify.testify.entity.UserRole;
import com.testify.testify.exception.DuplicateResourceException;
import com.testify.testify.mapper.UserMapper;
import com.testify.testify.repository.UserRepository;
import com.testify.testify.security.JwtService;
import com.testify.testify.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username '" + request.getUsername() + "' is already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("An account with email '" + request.getEmail() + "' already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(UserRole.TESTER); // default role; promote via a separate admin action
        user.setIsActive(true);

        User saved = userRepository.save(user);

        UserPrincipal principal = new UserPrincipal(saved);
        return buildAuthResponse(principal);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        UserPrincipal principal = new UserPrincipal(user);
        return buildAuthResponse(principal);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        String username;
        try {
            username = jwtService.extractUsername(refreshToken);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid or expired refresh token");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new BadCredentialsException("Invalid or expired refresh token");
        }

        return buildAuthResponse((UserPrincipal) userDetails);
    }

    private AuthResponse buildAuthResponse(UserPrincipal principal) {
        String accessToken = jwtService.generateAccessToken(principal);
        String refreshToken = jwtService.generateRefreshToken(principal);
        UserDto userDto = userMapper.toUserDto(principal.getUser());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTokenExpirySeconds())
                .user(userDto)
                .build();
    }
}
