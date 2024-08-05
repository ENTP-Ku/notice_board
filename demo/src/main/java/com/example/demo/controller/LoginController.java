// src/main/java/com/example/demo/controller/LoginController.java
package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new LoginResponse(true, "Login successful");
        }
        return new LoginResponse(false, "Invalid username or password");
    }

    @PostMapping("/create")
    public CreateResponse create(@RequestBody User userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            return new CreateResponse(false, "Username already exists");
        }
        if (userRepository.existsByUniqueCode(userRequest.getUniqueCode())) {
            return new CreateResponse(false, "Unique code already registered");
        }
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(userRequest); // 데이터베이스에 사용자 저장
        return new CreateResponse(true, "Registration successful");
    }

}

@Data
class LoginRequest {
    private String username;
    private String password;
}

@Data
class LoginResponse {
    private boolean success;
    private String message;

    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}

@Data
class CreateResponse {
    private boolean success;
    private String message;

    public CreateResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
