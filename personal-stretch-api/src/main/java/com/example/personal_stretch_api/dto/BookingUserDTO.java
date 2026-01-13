package com.example.personal_stretch_api.dto;

import java.time.LocalDateTime;

public record BookingUserDTO(
    Long id,
    String name,
    String email,
    String phone,
    LocalDateTime firstVisitDate,
    LocalDateTime lastVisitDate,
    LocalDateTime nextVisitDate,
    String memo,
    Long visitCount,
    LocalDateTime createdAt
) {
    
}
