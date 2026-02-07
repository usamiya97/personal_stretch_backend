package com.example.personal_stretch_api.dto;

import java.time.LocalDateTime;

import com.example.personal_stretch_api.model.Notification.NotificationType;

public record NotificationDTO(
    Long id,
    Long bookingId,
    String customerName,
    String message,
    NotificationType notificationType, // NEW, CANCEL, REMINDER
    boolean isRead,
    LocalDateTime bookingDate,
    LocalDateTime createdAt
) {
    
}
