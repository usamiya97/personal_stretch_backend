package com.example.personal_stretch_api.dto;

public record SetBookingFormDTO (
    Integer id,
    String name,
    String bookingDate,
    String startTime,
    Integer course,
    String status
){
    
}
