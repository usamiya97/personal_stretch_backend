package com.example.personal_stretch_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.personal_stretch_api.dto.TrainersDTO;
import com.example.personal_stretch_api.model.Role;
import com.example.personal_stretch_api.service.TrainersService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/api/v1")
@RestController
public class TrainerLoginController {

    private final TrainersService trainersService;

    public TrainerLoginController(TrainersService trainersService) {
        this.trainersService = trainersService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> trainersLogin(@Valid @RequestBody TrainersDTO trainersDTO) {
        boolean loginCheck = trainersService.loginCheck(trainersDTO);

        if (!loginCheck) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("failLogin","ログインに失敗しました。"));
        }

        Optional<Role> role = trainersService.getRoleName(trainersDTO);
        // セキュリティ JWT生成
        String accessToken = trainersService.getAccessToken(trainersDTO);
        String refreshToken = trainersService.getRefreshToken(trainersDTO);

        ResponseCookie refreshCookie = trainersService.createRefreshCookie(refreshToken);

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .body(Map.of(
                "accessToken",accessToken,
                "successLogin","ログインに成功しました。",
                "role",role));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> trainerLogout(HttpServletResponse response) {

        trainersService.clearRefreshCookie(response);
        
        return ResponseEntity.ok().body(Map.of("message", "ログアウトしました。"));
    }
    
}
