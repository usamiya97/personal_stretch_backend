package com.example.personal_stretch_api.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.personal_stretch_api.dto.NotificationDTO;
import com.example.personal_stretch_api.model.Notification;
import com.example.personal_stretch_api.repository.NotificationRepository;
import com.example.personal_stretch_api.service.NotificationService;

@RequestMapping("/api/v1")
@RestController
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    
    public NotificationController (NotificationRepository notificationRepository, NotificationService notificationService) {
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
    }

    // GET: 通知一覧を取得
    @GetMapping("/notification")
    public ResponseEntity<?> getNotifications() {
        // DTOへの変換ロジックは省略（Entityをそのまま返すと循環参照などのリスクがあるためDTO推奨）
        List<NotificationDTO> notifications = notificationRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        System.out.println("デバッグ"  + notifications);

        return ResponseEntity.ok(Map.of("notifications",notifications));
    }

    // PUT: 指定した通知を既読にする
    @PutMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }
    
    // PUT: 全て既読にする
    @PutMapping("/read-all")
    public void markAllAsRead() {
        notificationService.markAllAsRead();
    }

    private NotificationDTO convertToDto(Notification entity) {
        // nullチェックなどは適宜追加
        String title = "";
        if (entity.getBooking() != null) {
             // Bookingエンティティにtitleフィールドがない場合は、
             // getCustomer().getName() + "様" など、表示したい文字列を作る
             // 今回は仮に "予約ID: " + id とします
             title = "予約ID: " + entity.getBooking().getId();
        }

        return new NotificationDTO(
            entity.getId(),
            entity.getBooking() != null ? entity.getBooking().getId() : null,
            title,
            entity.getNotificationType(),
            entity.getMessage(),
            entity.isRead(),
            entity.getCreatedAt()
        );
    }
}
