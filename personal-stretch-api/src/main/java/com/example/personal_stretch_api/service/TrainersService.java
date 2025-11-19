package com.example.personal_stretch_api.service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.personal_stretch_api.config.JwtUtil;
import com.example.personal_stretch_api.dto.TrainersDTO;
import com.example.personal_stretch_api.model.Role;
import com.example.personal_stretch_api.model.Trainers;
import com.example.personal_stretch_api.repository.RoleRepository;
import com.example.personal_stretch_api.repository.TrainersRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class TrainersService {
    private final TrainersRepository trainersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;

    public TrainersService 
        (
            TrainersRepository trainersRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            RoleRepository roleRepository
        ) {
            this.trainersRepository = trainersRepository;
            this.passwordEncoder = passwordEncoder;
            this.jwtUtil = jwtUtil;
            this.roleRepository = roleRepository;
    }

    public void set(TrainersDTO trainersDTO) {
        createTrainerEntity(trainersDTO);
    }

    private void createTrainerEntity(TrainersDTO trainersDTO) {
        Trainers trainers = new Trainers();
        String hashPassword = passwordEncoder.encode(trainersDTO.adminPassword());
        trainers.setAdminName(trainersDTO.adminName());
        trainers.setAdminPassword(hashPassword);
        trainers.setRoleId(1);

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
        Trainers trainer = trainersRepository.findByAdminName(trainersDTO.adminName())
            .orElseThrow(() -> new JwtException("User not found in DB."));

        Optional<Role> role = roleRepository.findById(trainer.getRoleId());
        String roleName = "ROLE_" + role.get().getRoleName();
        // セキュリティ JWT生成
        String accessToken = jwtUtil.generateAccessToken(trainersDTO.adminName(),List.of(roleName));

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

    // アクセストークン発行
    public String refreshAccessToken(String refreshToken, HttpServletResponse response) {
        // 1. RTの検証 (ここでJwtExceptionが出る可能性がある)
        Jws<Claims> claims = jwtUtil.parse(refreshToken);
        String username = claims.getBody().getSubject();
    
        // 2. ユーザーが存在するか確認 (DBルックアップ)
        Trainers trainer = trainersRepository.findByAdminName(username)
            .orElseThrow(() -> new JwtException("User not found in DB."));

        Optional<Role> role = roleRepository.findById(trainer.getRoleId());
        String roleName = "ROLE_" + role.get().getRoleName();
    
        // 3. 新しいATを生成
        String newAccessToken = jwtUtil.generateAccessToken(username, List.of(roleName)); 
        
        // (オプション) 4. RTをローテーション（再発行）する場合
        // セキュリティ強化のため、RTも更新して新しいCookieをセットする
        String newRefreshToken = jwtUtil.generateRefreshToken(username);
        ResponseCookie refreshCookie = createRefreshCookie(newRefreshToken);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        
        return newAccessToken;
    }

    // Cookieをクリアするためのユーティリティメソッドも追加
    public void clearRefreshCookie(HttpServletResponse response) {
        ResponseCookie expiredCookie = ResponseCookie.from("refresh_token", "")
            .httpOnly(true)
            .secure(true) // 本番環境ではtrue
            .path("/")
            .maxAge(0) // 有効期限をゼロにして削除
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString());
    }
    
}
