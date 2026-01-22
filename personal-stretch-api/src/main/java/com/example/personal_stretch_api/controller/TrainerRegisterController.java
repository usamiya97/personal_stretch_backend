package com.example.personal_stretch_api.controller;

import com.example.personal_stretch_api.service.TrainersService;
import com.example.personal_stretch_api.dto.TrainersDTO;
import com.example.personal_stretch_api.model.Trainers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * トレーナー（管理者）登録
 */
@RestController
@RequestMapping("/api/v1")
public class TrainerRegisterController {
    private static final Logger logger = LoggerFactory.getLogger(TrainerRegisterController.class);


    private final TrainersService trainersService;

    public TrainerRegisterController(TrainersService trainersService) {
        this.trainersService = trainersService;
    }


    @PostMapping("trainers")
    public ResponseEntity<?> setTrainers(@RequestBody TrainersDTO trainersDTO) {
        try {
            // 管理者登録
            trainersService.set(trainersDTO);
        } catch (IllegalArgumentException e) { 
            logger.error("管理者登録失敗しました");
        }

        return ResponseEntity.ok(Map.of(
               "success","成功"));
    }

    // 管理者ユーザー（トレーナー）を取得
    @GetMapping("/adminUsers")
    public ResponseEntity<?> getAdminUsers() {

        List<Trainers> trainers = trainersService.getAdminUsers();

        return ResponseEntity.ok(Map.of("trainers",trainers));
    }
}
