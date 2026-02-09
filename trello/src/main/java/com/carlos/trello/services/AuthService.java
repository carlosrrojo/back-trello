package com.carlos.trello.services;

import com.carlos.trello.mapper.UserMapper;
import com.carlos.trello.bean.UserDTO;
import com.carlos.trello.config.JwtUtil;
import com.carlos.trello.persistence.model.CustomUser;
import com.carlos.trello.bean.LoginRequest;
import com.carlos.trello.bean.RegisterRequest;
import com.carlos.trello.persistence.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;

    private JwtUtil jwtUtils;

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public AuthService(UserRepository userRepository,
        AuthenticationManager authenticationManager,
        JwtUtil jwtUtils,
        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(LoginRequest loginData) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword())
        );

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }

    public UserDTO saveUser(CustomUser user) {
        Optional<CustomUser> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            //throw new UserAlreadyException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        CustomUser savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO saveUser(RegisterRequest request) {
        CustomUser user = userMapper.toEntity(request);
        Optional<CustomUser> existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            //throw new UserAlreadyExistsException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        CustomUser savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO getUserByUsername(String username) {
        Optional<CustomUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return userMapper.toDTO(user.get());
    }
}
