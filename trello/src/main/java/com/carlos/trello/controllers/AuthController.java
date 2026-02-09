package com.carlos.trello.controllers;

import com.carlos.trello.services.AuthService;
import com.carlos.trello.bean.RegisterRequest;
import com.carlos.trello.bean.LoginRequest;
import com.carlos.trello.persistence.model.CustomUser;
import com.carlos.trello.bean.UserDTO;
import com.carlos.trello.config.JwtUtil;
import com.carlos.trello.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.User;

import java.util.HashMap;
import java.util.Map;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    } 

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginData) {
        String authenticated = authService.authenticate(loginData);
        Map<String, Object> response = new HashMap<>();
        if (authenticated == null) {
            response.put("authenticated", false);
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(401).body(response);
        }
        response.put("authenticated", authenticated);
        response.put("token", authenticated);
        response.put("message", "Login successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerData) {
        UserDTO savedUser = authService.saveUser(registerData);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registration successful");
        response.put("user", savedUser);
        return ResponseEntity.ok(response);
    }
}
