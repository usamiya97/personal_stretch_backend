package com.example.personal_stretch_api.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(
    Long id,
    @NotBlank(message = "ユーザー名は必須です")
    String name,
    String email,
    String phone,
    String memo
) {
    
}
