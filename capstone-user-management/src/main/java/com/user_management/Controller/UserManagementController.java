package com.user_management.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.user_management.Feign.AuthClient;
import com.user_management.dto.AdminCreateUserRequest;
import com.user_management.dto.AdminUpdateUserRequest;
import com.user_management.dto.UserResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/users")
public class UserManagementController {

    private final AuthClient authClient;

    public UserManagementController(AuthClient authClient) {
        this.authClient = authClient;
    }

    private void validateAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Forbidden: Admin access required");
        }
    }

    private void validateAdminOrAgent(String role) {
        if (!"ADMIN".equals(role) && !"INSURANCE_AGENT".equals(role) && !"HOSPITAL".equals(role)) {
            throw new RuntimeException("Forbidden: Admin or Insurance Agent access required");
        }
    }

    @GetMapping
    public List<UserResponse> getAll(
            @RequestHeader("X-User-Role") String role) {

        validateAdminOrAgent(role);
        return authClient.getAllUsers();
    }

    @PostMapping
    public UserResponse create(
            @RequestHeader("X-User-Role") String role,
            @RequestBody @Valid AdminCreateUserRequest req) {

        validateAdmin(role);
        return authClient.createUser(req);
    }

    @PutMapping("/{id}")
    public UserResponse update(
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long id,
            @RequestBody @Valid AdminUpdateUserRequest req) {

        validateAdmin(role);
        return authClient.updateUser(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long id) {

        validateAdmin(role);
        authClient.deleteUser(id);
    }
}



