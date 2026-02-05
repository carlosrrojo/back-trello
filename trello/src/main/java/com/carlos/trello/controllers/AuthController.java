package com.carlos.trello.controllers;

import com.carlos.trello.persistence.model.User;
import com.carlos.trello.persistence.repo.UserRepository;
import com.carlos.trello.services.AuthService;
import com.carlos.trello.bean.UserDTO;
import com.carlos.trello.config.JwtUtil;
import com.carlos.trello.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
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
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO, @RequestParam String password) {
        String username = userDTO.getUsername();
        
        if (authService.getUserByUsername(username) != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Username already exists");
            return ResponseEntity.status(400).body(response);
        }
        
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registration successful");
        return ResponseEntity.ok(response);
    }
}
