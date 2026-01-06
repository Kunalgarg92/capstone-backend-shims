package com.auth_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth_service.dto.AdminCreateUserRequest;
import com.auth_service.dto.AdminUpdateUserRequest;
import com.auth_service.dto.UserResponse;
import com.auth_service.entity.Role;
import com.auth_service.entity.User;
import com.auth_service.repository.UserRepository;
import com.auth_service.service.AdminUserService;
import com.auth_service.service.EmailService;

@ExtendWith(MockitoExtension.class)
class AdminUserServiceTest {

    @Mock
    private UserRepository repo;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AdminUserService service;

    @Test
    void createUser_success() {
        AdminCreateUserRequest req =
                new AdminCreateUserRequest("a@test.com", "pass", Role.ADMIN);

        when(repo.findByEmail(any())).thenReturn(Optional.empty());
        when(encoder.encode(any())).thenReturn("hashed");
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        UserResponse res = service.createUser(req);

        assertEquals("a@test.com", res.email);
        verify(emailService).sendUserCreationEmail(any(), any(), any());
    }

    @Test
    void createUser_duplicateEmail() {
        when(repo.findByEmail(any())).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class,
                () -> service.createUser(
                        new AdminCreateUserRequest("a@test.com", "p", Role.ADMIN)));
    }

    @Test
    void updateUser_success() {
        User user = new User();
        user.setId(1L);

        when(repo.findById(1L)).thenReturn(Optional.of(user));
        when(repo.save(any())).thenReturn(user);

        UserResponse res = service.updateUser(
                1L, new AdminUpdateUserRequest(Role.CUSTOMER, false));

        assertEquals("CUSTOMER", res.role);
    }
}
