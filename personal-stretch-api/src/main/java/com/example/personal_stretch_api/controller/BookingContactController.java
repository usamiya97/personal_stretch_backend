package com.example.personal_stretch_api.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.personal_stretch_api.dto.SetBookingFormDTO;
import com.example.personal_stretch_api.service.BookingService;

@RestController
@RequestMapping("/api/v1")
public class BookingContactController {

    private final BookingService bookingService;

    public BookingContactController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking-contacts")
    public ResponseEntity<?> setBooking(@RequestBody SetBookingFormDTO setBookingFormDTO) {

        bookingService.setBooking(setBookingFormDTO);
        return ResponseEntity.ok(Map.of("success","予約完了"));
    }
    
}
