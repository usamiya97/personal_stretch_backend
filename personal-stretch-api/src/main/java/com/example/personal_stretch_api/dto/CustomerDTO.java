package com.example.personal_stretch_api.dto;

public record CustomerDTO(
    Long id,
    String name,
    String email,
    String phone,
    String memo
) {
    
}
