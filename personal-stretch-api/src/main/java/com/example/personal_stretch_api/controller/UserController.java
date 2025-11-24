package com.example.personal_stretch_api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.personal_stretch_api.dto.BookingUserDTO;
import com.example.personal_stretch_api.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        List<BookingUserDTO> bookingUsers = userService.getUsers();

        return ResponseEntity.ok(Map.of("bookingUsers",bookingUsers));
    }
    
}
