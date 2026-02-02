package com.example.personal_stretch_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.personal_stretch_api.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
        
}
