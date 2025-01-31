package com.odeon.service;

import com.odeon.dto.UserDTO;
import com.odeon.dto.UserRegistrationDTO;
import com.odeon.entity.User;
import com.odeon.repository.UserRepository;
import com.odeon.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public UserDTO register(UserRegistrationDTO registrationDTO) {
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setCity(registrationDTO.getCity());

        user = userRepository.save(user);
        return mapToDTO(user);
    }

    public UserDTO findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::mapToDTO)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public boolean authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }

    public Map<String, Object> login(String username, String password) {
        if (!authenticate(username, password)) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        UserDTO userDTO = findByUsername(username);
        String token = tokenProvider.generateToken(username);

        Map<String, Object> response = new HashMap<>();
        response.put("user", userDTO);
        response.put("token", token);

        return response;
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getCity(),
                user.getRole()
        );
    }
} 
