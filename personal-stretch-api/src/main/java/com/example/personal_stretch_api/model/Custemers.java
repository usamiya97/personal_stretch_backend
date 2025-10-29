package com.example.personal_stretch_api.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class Custemers {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private long id;

    @Column(name = "customers_name")
    private String userName;

    @Column(name = "customers_email")
    private String userEmail;

    @Column(name = "customers_phone_number")
    private String userPhoneNumber;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}

