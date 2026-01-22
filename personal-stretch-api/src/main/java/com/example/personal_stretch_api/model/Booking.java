package com.example.personal_stretch_api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bookings")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customers customers;

    @Column(name = "status")
    private String status;

    @Column(name = "message")
    private String message;

    @Column(name = "first_choice_datetime")
    private LocalDateTime firstChoiceDateTime;

    @Column(name = "second_choice_datetime")
    private LocalDateTime secondChoiceDateTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "choise_stretch")
    private int choiseStretch;
    
}
