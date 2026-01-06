package com.user_management.Feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.user_management.dto.AdminCreateUserRequest;
import com.user_management.dto.AdminUpdateUserRequest;
import com.user_management.dto.UserResponse;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @GetMapping("/internal/admin/users")
    List<UserResponse> getAllUsers();

    @PostMapping("/internal/admin/users")
    UserResponse createUser(@RequestBody AdminCreateUserRequest req);

    @PutMapping("/internal/admin/users/{id}")
    UserResponse updateUser(
            @PathVariable Long id,
            @RequestBody AdminUpdateUserRequest req
    );

    @DeleteMapping("/internal/admin/users/{id}")
    void deleteUser(@PathVariable Long id);
}
