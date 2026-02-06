package com.carlos.trello.controllers;

import com.carlos.trello.services.AuthService;
import com.carlos.trello.bean.RegisterRequest;
import com.carlos.trello.bean.UserDTO;
import com.carlos.trello.config.JwtUtil;
import com.carlos.trello.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    public AuthController(AuthService authService){
        this.authService = authService;
    } 

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        boolean authenticated = authService.authenticate(username, password);
        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", authenticated);
        if (authenticated) {
            String token = jwtUtil.generateToken(username);
            response.put("token", token);
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, PasswordEncoder passwordEncoder) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserDTO savedUser = authService.saveUser(request);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registration successful");
        response.put("user", savedUser);
        return ResponseEntity.ok(response);
    }
}
