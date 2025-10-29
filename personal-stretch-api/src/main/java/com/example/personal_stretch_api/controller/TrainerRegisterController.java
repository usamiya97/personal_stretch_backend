package com.example.personal_stretch_api.controller;

import com.example.personal_stretch_api.service.TrainersService;
import com.example.personal_stretch_api.dto.TrainersDTO;

import java.util.Map;

import org.springframework.http.ResponseEntity;
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

    private final TrainersService trainersService;

    public TrainerRegisterController(TrainersService trainersService) {
        this.trainersService = trainersService;
    }


    @PostMapping("register")
    public ResponseEntity<?> setTrainers(@RequestBody TrainersDTO trainersDTO) {
        try {
            trainersService.set(trainersDTO);


        } catch (IllegalArgumentException e) {
            

        }

        return ResponseEntity.ok(Map.of(
               "success","成功"));

    
    }
    
}
