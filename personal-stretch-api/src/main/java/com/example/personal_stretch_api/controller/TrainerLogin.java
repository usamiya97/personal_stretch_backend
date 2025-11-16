package com.example.personal_stretch_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.personal_stretch_api.dto.TrainersDTO;
import com.example.personal_stretch_api.service.TrainersService;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/api/v1")
@RestController
public class TrainerLogin {

    private final TrainersService trainersService;

    public TrainerLogin(TrainersService trainersService) {
        this.trainersService = trainersService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> trainersLogin(@RequestBody TrainersDTO trainersDTO) {
        boolean loginCheck = trainersService.loginCheck(trainersDTO);

        if (!loginCheck) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("failLogin","ログインに失敗しました。"));
        }
        // セキュリティ JWT生成
        String accessToken = trainersService.getAccessToken(trainersDTO);
        String refreshToken = trainersService.getRefreshToken(trainersDTO);

        ResponseCookie refreshCookie = trainersService.createRefreshCookie(refreshToken);

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .body(Map.of("accessToken",accessToken,"successLogin","ログインに成功しました。"));
    }
}
