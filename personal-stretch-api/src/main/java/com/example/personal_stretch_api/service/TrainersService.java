package com.example.personal_stretch_api.service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.personal_stretch_api.config.JwtUtil;
import com.example.personal_stretch_api.dto.TrainersDTO;
import com.example.personal_stretch_api.model.Trainers;
import com.example.personal_stretch_api.repository.TrainersRepository;

@Service
public class TrainersService {
    private final TrainersRepository trainersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public TrainersService (TrainersRepository trainersRepository,PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
        this.trainersRepository = trainersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void set(TrainersDTO trainersDTO) {
        createTrainerEntity(trainersDTO);
    }

    private void createTrainerEntity(TrainersDTO trainersDTO) {
        Trainers trainers = new Trainers();
        String hashPassword = passwordEncoder.encode(trainersDTO.adminPassword());
        trainers.setAdminName(trainersDTO.adminName());
        trainers.setAdminPassword(hashPassword);

        trainersRepository.save(trainers);
    }

    public boolean loginCheck(TrainersDTO trainersDTO) {
        String rawPassword = trainersDTO.adminPassword();
        Optional<Trainers> trainers = trainersRepository.findByAdminName(trainersDTO.adminName());

        if (trainers.isPresent()) {
            String hashedPassword = trainers.get().getAdminPassword();
            return checkPassword(rawPassword, hashedPassword);
        }
        return false;   
    }

    // アクセストークン取得
    public String getAccessToken(TrainersDTO trainersDTO) {
        // セキュリティ JWT生成
        String accessToken = jwtUtil.generateAccessToken(trainersDTO.adminName(),List.of("USER"));

        return accessToken;
    }

    // リフレッシュトークン取得
    public String getRefreshToken(TrainersDTO trainersDTO) {
        // セキュリティ JWT生成
        String refreshToken = jwtUtil.generateRefreshToken(trainersDTO.adminName());

        return refreshToken;
    }

    public ResponseCookie createRefreshCookie(String refresh) {
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refresh)
            .httpOnly(true)
            .secure(false)
            .domain("")
            .path("/") // もしくは "/auth/refresh" に限定
            .sameSite("Lax")
            .maxAge(Duration.ofDays(jwtUtil.getRefreshDays()))
            .build();
        
        return refreshCookie;
    }

    private boolean checkPassword(String rawPassword,String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
    
}
