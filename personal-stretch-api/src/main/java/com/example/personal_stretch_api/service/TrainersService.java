package com.example.personal_stretch_api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.personal_stretch_api.dto.TrainersDTO;
import com.example.personal_stretch_api.model.Trainers;
import com.example.personal_stretch_api.repository.TrainersRepository;

@Service
public class TrainersService {
    private final TrainersRepository trainersRepository;
    private final PasswordEncoder passwordEncoder;

    public TrainersService (TrainersRepository trainersRepository,PasswordEncoder passwordEncoder) {
        this.trainersRepository = trainersRepository;
        this.passwordEncoder = passwordEncoder;
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

    private boolean checkPassword(String rawPassword,String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
    
}
