package com.auth_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.auth_service.Security.JwtUtil;
import com.auth_service.dto.AuthResponse;
import com.auth_service.dto.LoginRequest;
import com.auth_service.dto.RegisterRequest;
import com.auth_service.dto.UserResponse;
import com.auth_service.entity.Role;
import com.auth_service.entity.User;
import com.auth_service.repository.UserRepository;
import com.auth_service.service.AuthService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository repo;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService service;

    @Test
    void register_success() {
        when(repo.findByEmail(any())).thenReturn(Optional.empty());
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        UserResponse res =
                service.register(new RegisterRequest("a@test.com", "pass"));

        assertEquals("a@test.com", res.email);
    }

    @Test
    void register_duplicateEmail() {
        when(repo.findByEmail(any())).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class,
                () -> service.register(new RegisterRequest("a@test.com", "pass")));
    }

    @Test
    void login_success() {
        User user = new User();
        user.setId(1L);
        user.setEmail("a@test.com");
        user.setPassword(new BCryptPasswordEncoder().encode("pass"));
        user.setRole(Role.CUSTOMER);

        when(repo.findByEmail(any())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(any(), any())).thenReturn("token");

        AuthResponse res =
                service.login(new LoginRequest("a@test.com", "pass"));

        assertEquals("token", res.accessToken);
    }

    @Test
    void login_invalidPassword() {
        User user = new User();
        user.setPassword("wrong");

        when(repo.findByEmail(any())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class,
                () -> service.login(new LoginRequest("a@test.com", "pass")));
    }
}
