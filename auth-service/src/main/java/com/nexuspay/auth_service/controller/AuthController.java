package com.nexuspay.auth_service.controller;

import com.nexuspay.auth_service.dto.AuthRequest;
import com.nexuspay.auth_service.model.User;
import com.nexuspay.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        return authService.login(request.getUsername(), request.getPassword());
    }
}