package com.example.personal_stretch_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.personal_stretch_api.model.Booking;
import com.example.personal_stretch_api.service.BookingService;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RequestMapping("/api/v1")
@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> getBookingList() {
        List<Booking> bookingList = bookingService.getBookings();

        return ResponseEntity.ok(Map.of("bookingList",bookingList));
    }
    
}
