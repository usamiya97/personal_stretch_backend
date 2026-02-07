package com.example.personal_stretch_api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "notifications")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Bookingテーブルとのリレーション
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(name = "notification_type", nullable = false)
    @Enumerated(EnumType.STRING) // 文字列としてDBに保存
    private NotificationType notificationType; // NEW, CANCEL, REMINDER

    @Column(name = "is_read")
    private boolean isRead = false;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum NotificationType {
        NEW("予約が入りました"),
        CANCEL("予約がキャンセルされました"),
        TODAY("本日の予約があります"),
        TOMORROW("明日の予約があります");

        private final String defaultMessage;

        NotificationType(String defaultMessage) {
            this.defaultMessage = defaultMessage;
        }

        public String getDefaultMessage() {
            return defaultMessage;
        }
    }
}
