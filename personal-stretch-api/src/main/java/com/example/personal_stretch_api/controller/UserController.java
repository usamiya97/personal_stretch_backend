package com.example.personal_stretch_api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.personal_stretch_api.dto.BookingUserDTO;
import com.example.personal_stretch_api.dto.CustomerDTO;
import com.example.personal_stretch_api.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        // List<BookingUserDTO> bookingUsers = userService.getUsers();
        List<BookingUserDTO> bookingUsers = userService.getUsers();

        return ResponseEntity.ok(Map.of("bookingUsers",bookingUsers));
    }

    // 新規ユーザー登録
    @PostMapping("/setCustomer")
    public ResponseEntity<?> setCustomer(@RequestBody CustomerDTO customerDTO) {
        userService.setCustomer(customerDTO);
        
        return ResponseEntity.ok(Map.of("success","更新に成功しました。"));
    }

    // 既存ユーザー更新
    @PostMapping("/updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        userService.updateCustomer(customerDTO);
        
        return ResponseEntity.ok(Map.of("success","更新に成功しました。"));
    }
    
    
    
}
