package com.auth_service.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth_service.dto.AdminCreateUserRequest;
import com.auth_service.dto.AdminUpdateUserRequest;
import com.auth_service.dto.UserResponse;
import com.auth_service.entity.User;
import com.auth_service.repository.UserRepository;

@Service
public class AdminUserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AdminUserService(
            UserRepository repo,
            PasswordEncoder passwordEncoder,
            EmailService emailService) {

        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public List<UserResponse> getAllUsers() {
        return repo.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    public UserResponse createUser(AdminCreateUserRequest req) {

        if (repo.findByEmail(req.email()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        String rawPassword = req.password();
        User user = new User();
        user.setEmail(req.email());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(req.role());
        user.setActive(true);

        User savedUser = repo.save(user);
        emailService.sendUserCreationEmail(
                savedUser.getEmail(),
                savedUser.getRole().name(),
                rawPassword
        );


        return UserResponse.from(savedUser);
    }

    public UserResponse updateUser(Long id, AdminUpdateUserRequest req) {

        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(req.role());
        user.setActive(req.active());

        return UserResponse.from(repo.save(user));
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }
}
