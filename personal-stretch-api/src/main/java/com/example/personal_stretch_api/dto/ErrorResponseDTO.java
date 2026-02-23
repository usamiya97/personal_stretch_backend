package com.example.personal_stretch_api.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path
) {
    
}
