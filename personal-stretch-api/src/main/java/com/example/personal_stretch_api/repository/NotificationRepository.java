package com.example.personal_stretch_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.personal_stretch_api.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{

    List<Notification> findByIsReadFalseOrderByCreatedAtDesc();

    List<Notification> findAllByOrderByCreatedAtDesc();
}
