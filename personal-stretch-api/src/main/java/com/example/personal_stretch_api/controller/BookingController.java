package com.example.personal_stretch_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.personal_stretch_api.dto.BookingFormDTO;
import com.example.personal_stretch_api.dto.DetailBooking;
import com.example.personal_stretch_api.model.Booking;
import com.example.personal_stretch_api.service.BookingService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<?> getBookingList(
        @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (startDate != null && endDate != null) {
            // 月単位（指定期間）のデータを取得
            List<Booking> rangeBookings = bookingService.getBookingsByRange(startDate, endDate);
            return ResponseEntity.ok(Map.of("bookingList", rangeBookings));
        } else {
            // 指定がない場合は全件（またはデフォルトで今月分を返すなどの処理）
            List<Booking> allBookings = bookingService.getBookings();
            return ResponseEntity.ok(Map.of("bookingList", allBookings));
        }
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

    @PutMapping("/bookings/{id}")
    public ResponseEntity<?> DetailBookingGet(@PathVariable("id") Long id, @RequestBody DetailBooking detailBooking) {

        try {
            // 予約顧客情報登録
            bookingService.updateBookingData(id,detailBooking);

            return ResponseEntity.ok(Map.of("success","更新に成功しました。"));

        } catch (Exception e) {
            System.out.println("エラー" + e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "更新に失敗しました。"));
        }
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<?> DeleteBooking(@PathVariable("id") Long id) {

        try {
            // 予約顧客情報登録
            bookingService.deleteBookingData(id);

            return ResponseEntity.ok(Map.of("success","削除に成功しました。"));

        } catch (Exception e) {
            System.out.println("エラー" + e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "削除に失敗しました。"));
        }
    }
}
