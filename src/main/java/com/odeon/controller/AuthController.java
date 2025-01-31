package com.odeon.controller;

import com.odeon.dto.UserDTO;
import com.odeon.dto.UserRegistrationDTO;
import com.odeon.model.ApiResponse;
import com.odeon.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@RequestBody UserRegistrationDTO registrationDTO) {
        UserDTO userDTO = userService.register(registrationDTO);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "User registered successfully", userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(
            @RequestParam String username,
            @RequestParam String password) {
        Map<String, Object> loginResponse = userService.login(username, password);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Login successful", loginResponse));
    }
} 
