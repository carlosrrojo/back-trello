// src/test/java/com/carlos/trello/controllers/AuthControllerTest.java
package com.carlos.trello.controllers;

import com.carlos.trello.persistence.model.CustomUser;
import com.carlos.trello.persistence.repo.UserRepository;
import com.carlos.trello.services.AuthService;
import com.carlos.trello.bean.RegisterRequest;
import com.carlos.trello.bean.UserDTO;
import com.carlos.trello.config.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.carlos.trello.config.UserDetailsImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void loginSuccess() throws Exception {
        String username = "user1";
        String password = "pass";
        String token = "jwt-token";

        Authentication auth = new UsernamePasswordAuthenticationToken(
            new UserDetailsImpl("1", username, password,  Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))), null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(auth);
        when(jwtUtil.generateToken(eq(username))).thenReturn("jwt-token");
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authenticated", is(true)))
                .andExpect(jsonPath("$.token", is(token)))
                .andExpect(jsonPath("$.message", is("Login successful")));
    }
    /*
    @Test
    void loginFailure_returnsUnauthorizedAndMessage() throws Exception {
        String username = "user1";
        String password = "wrong";

        when(authService.authenticate(eq(username), eq(password))).thenReturn(false);

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.authenticated", is(false)))
                .andExpect(jsonPath("$.message", is("Invalid credentials")));

        verify(authService, times(1)).authenticate(eq(username), eq(password));
        verifyNoInteractions(jwtUtil);
    }

    @Test
    void registerSuccess_savesUserAndReturnsMessage() throws Exception {
        String username = "newuser";
        String password = "newpass";
        String encoded = "encodedPass";

        when(authService.getUserByUsername(eq(username))).thenReturn(null);
        when(passwordEncoder.encode(eq(password))).thenReturn(encoded);
        when(userRepository.save(any(CustomUser.class))).thenAnswer(i -> i.getArgument(0));

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Registration successful")));

        ArgumentCaptor<CustomUser> userCaptor = ArgumentCaptor.forClass(CustomUser.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        CustomUser saved = userCaptor.getValue();
        assertEquals(username, saved.getUsername());
        assertEquals(encoded, saved.getPassword());
    }

    @Test
    void registerUsernameExists_returnsBadRequestAndMessage() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existing");
        request.setPassword("pass");

        when(authService.saveUser(eq(request))).thenReturn(new UserDTO("", request.getUsername()));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Username already exists")));

        verify(userRepository, never()).save(any());
    }*/
}