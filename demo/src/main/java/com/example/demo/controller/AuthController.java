package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            // 로그인 성공
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            // 로그인 실패
            return ResponseEntity.ok(Map.of("success", false, "message", "아이디 또는 비밀번호가 잘못되었습니다."));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        String uniqueCode = credentials.get("uniqueCode");

        if (username == null || password == null || uniqueCode == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "모든 필드를 입력하세요."));
        }

        // Check if username already exists
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "아이디가 이미 존재합니다."));
        }

        String encodedPassword = passwordEncoder.encode(password);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        newUser.setUniqueCode(uniqueCode);

        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("success", true));
    }



}
