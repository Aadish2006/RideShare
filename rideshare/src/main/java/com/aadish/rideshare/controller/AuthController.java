package com.aadish.rideshare.controller;

import com.aadish.rideshare.dto.AuthRequest;
import com.aadish.rideshare.dto.AuthResponse;
import com.aadish.rideshare.dto.RegisterRequest;
import com.aadish.rideshare.service.AuthService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    public final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest req) {
        return authService.login(req);
    }
}
