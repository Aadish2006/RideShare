package com.aadish.rideshare.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    // ROLE_USER / ROLE_DRIVER
    @NotBlank
    private String role;
}
