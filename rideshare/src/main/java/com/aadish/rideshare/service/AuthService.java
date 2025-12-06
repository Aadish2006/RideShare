package com.aadish.rideshare.service;

import com.aadish.rideshare.dto.AuthRequest;
import com.aadish.rideshare.dto.AuthResponse;
import com.aadish.rideshare.dto.RegisterRequest;
import com.aadish.rideshare.exception.BadRequestException;
import com.aadish.rideshare.model.User;
import com.aadish.rideshare.repository.UserRepository;
import com.aadish.rideshare.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists");
        }

        if (!req.getRole().equals("ROLE_USER") && !req.getRole().equals("ROLE_DRIVER")) {
            throw new BadRequestException("Role must be ROLE_USER or ROLE_DRIVER");
        }

        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .build();

        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(),
                            req.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new BadRequestException("Invalid username or password");
        }

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new BadRequestException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        return new AuthResponse(token);
    }
}
