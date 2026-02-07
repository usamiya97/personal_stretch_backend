package com.example.personal_stretch_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import com.example.personal_stretch_api.model.Booking;
import com.example.personal_stretch_api.model.Notification;
import com.example.personal_stretch_api.repository.BookingRepository;
import com.example.personal_stretch_api.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final BookingRepository bookingRepository; // 既存の予約リポジトリ

    // 1. 通知を作成するメソッド（予約作成時などに呼ぶ）
    @Transactional
    public void createNotification(Long bookingId, Notification.NotificationType type, String message) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

        Notification notification = new Notification();
        notification.setBooking(booking);
        notification.setNotificationType(type);
        
        notificationRepository.save(notification);
    }

    // 2. 通知を既読にするメソッド（APIから呼ばれる）
    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification); // 更新
    }
    
    // 3. 全既読にする
    @Transactional
    public void markAllAsRead() {
        List<Notification> unreads = notificationRepository.findByIsReadFalseOrderByCreatedAtDesc();
        unreads.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unreads);
    }
}
