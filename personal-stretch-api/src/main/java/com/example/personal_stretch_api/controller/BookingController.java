package com.example.personal_stretch_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.personal_stretch_api.dto.BookingFormDTO;
import com.example.personal_stretch_api.dto.DetailBooking;
import com.example.personal_stretch_api.model.Booking;
import com.example.personal_stretch_api.service.BookingService;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



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

    @PostMapping("/bookings")
    public ResponseEntity<?> RegistContactForm(@RequestBody BookingFormDTO bookingFormDTO) {

        try {
            // 予約顧客情報登録
            bookingService.RegistContactForm(bookingFormDTO);

            return ResponseEntity.ok(Map.of("success","保存に成功しました。"));

        } catch (Exception e) {
            System.out.println("エラー" + e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "保存に失敗しました。"));
        }
    }

    @PatchMapping("/detailBooking")
    public ResponseEntity<?> DetailBookingGet(@RequestBody DetailBooking detailBooking) {

        try {
            // 予約顧客情報登録
            bookingService.updateBookingData(detailBooking);

            return ResponseEntity.ok(Map.of("success","保存に成功しました。"));

        } catch (Exception e) {
            System.out.println("エラー" + e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "保存に失敗しました。"));
        }
    }
    
    
}
