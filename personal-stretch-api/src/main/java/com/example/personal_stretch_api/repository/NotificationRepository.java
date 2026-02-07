package com.example.personal_stretch_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.personal_stretch_api.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{

    List<Notification> findByIsReadFalseOrderByCreatedAtDesc();

    List<Notification> findAllByOrderByCreatedAtDesc();

    @Query("SELECT n FROM Notification n " +
           "JOIN FETCH n.booking b " +       // Bookingを結合
           "JOIN FETCH b.customers c " +      // さらにBookingに紐づくCustomerを結合
           "ORDER BY n.createdAt DESC")
    List<Notification> findAllWithDetails();

    // 未読のみの場合も同様
    @Query("SELECT n FROM Notification n " +
           "JOIN FETCH n.booking b " +
           "JOIN FETCH b.customers c " +
           "WHERE n.isRead = false " +
           "ORDER BY n.createdAt DESC")
    List<Notification> findUnreadWithDetails();
}
