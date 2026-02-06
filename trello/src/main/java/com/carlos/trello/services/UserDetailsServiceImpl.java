package com.carlos.trello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

import com.carlos.trello.persistence.repo.UserRepository;
import com.carlos.trello.persistence.model.CustomUser;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CustomUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        CustomUser foundUser = user.get();

        return User
                .withUsername(foundUser.getUsername())
                .password(foundUser.getPassword())
                //.authorities("USER") // Simple authority for demonstration
                .build();
    }
}
