package com.example.personal_stretch_api.dto;

public record BookingFormDTO(
    String name,
    String email,
    String tel,
    String firstChoiceDate,
    String firstChoiceTime,
    String choiceStretch,
    String secondChoiceDate,
    String secondChoiceTime,
    String memo,
    String status
) {
} 
