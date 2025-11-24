package com.example.personal_stretch_api.dto;

import java.time.LocalDateTime;

public record BookingUserDTO(
    Long id,
    String name,
    String email,
    String phone,
    LocalDateTime lastVisitDate, // LocalDateTimeから日付のみに変換
    String message,
    Long visitCount
) {
    
}
