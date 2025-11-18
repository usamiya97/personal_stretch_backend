package com.example.personal_stretch_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.personal_stretch_api.model.Booking;
import com.example.personal_stretch_api.repository.BookingRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }
    
}
