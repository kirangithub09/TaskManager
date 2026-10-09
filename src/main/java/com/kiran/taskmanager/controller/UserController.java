package com.kiran.taskmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiran.taskmanager.dto.LoginRequest;
import com.kiran.taskmanager.dto.LoginResponse;
import com.kiran.taskmanager.dto.RegisterRequest;
import com.kiran.taskmanager.dto.UserResponse;
import com.kiran.taskmanager.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ========================= REGISTER =============================

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody @Valid RegisterRequest request) {

        UserResponse response = userService.register(request);

        return ResponseEntity.status(201).body(response);
    }

    // =========================== LOGIN ===============================

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request) {

        LoginResponse response = userService.login(request);

        return ResponseEntity.ok(response);
    }
}