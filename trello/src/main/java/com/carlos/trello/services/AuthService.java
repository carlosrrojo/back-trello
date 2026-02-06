package com.carlos.trello.services;

import com.carlos.trello.mapper.UserMapper;
import com.carlos.trello.bean.UserDTO;
import com.carlos.trello.persistence.model.CustomUser;
import com.carlos.trello.bean.RegisterRequest;
import com.carlos.trello.persistence.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticate(String username, String password) {
        Optional<CustomUser> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return true;
        }
        return false;
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
            //throw new UserAlreadyException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        CustomUser savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO getUserByUsername(String username) {
        Optional<CustomUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return userMapper.toDTO(user.get());
    }
}
