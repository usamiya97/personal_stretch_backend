package com.example.personal_stretch_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.personal_stretch_api.service.TrainersService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/api/v1")
@RestController
public class RefreshController {

    private final TrainersService trainersService;

    public RefreshController(TrainersService trainersService) {
        this.trainersService = trainersService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> getToken(
        @CookieValue("refresh_token") String refreshToken,
        HttpServletResponse response 
    ) {
        if (refreshToken == null) {
            // RTが存在しない場合は、認証情報がないため401を返す
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "リフレッシュトークンが存在しません。再ログインしてください。"));
        }
        try {
            // サービス層に検証と再発行を委譲
            String newAccessToken = trainersService.refreshAccessToken(refreshToken, response);

            // 成功レスポンス
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));

        } catch (JwtException e) {
            // RTの署名が無効、または期限切れの場合
            // セキュリティのため、不正なCookieをクリアすることが推奨される
            trainersService.clearRefreshCookie(response);
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "リフレッシュトークンが無効です。再ログインが必要です。"));
        }
    }
    
}
