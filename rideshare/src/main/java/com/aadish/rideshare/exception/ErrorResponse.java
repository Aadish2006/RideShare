package com.aadish.rideshare.exception;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class ErrorResponse {
    private String error;
    private String message;
    private Instant timestamp;
}
