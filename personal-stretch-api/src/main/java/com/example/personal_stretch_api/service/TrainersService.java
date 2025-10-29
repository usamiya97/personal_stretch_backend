package com.example.personal_stretch_api.service;

import org.springframework.stereotype.Service;

import com.example.personal_stretch_api.dto.TrainersDTO;
import com.example.personal_stretch_api.model.Trainers;
import com.example.personal_stretch_api.repository.TrainersRepository;

@Service
public class TrainersService {
    private final TrainersRepository trainersRepository;

    public TrainersService (TrainersRepository trainersRepository) {
        this.trainersRepository = trainersRepository;
    }

    public void set(TrainersDTO trainersDTO) {
        createTrainerEntity(trainersDTO);
    }

    private void createTrainerEntity(TrainersDTO trainersDTO) {
        Trainers trainers = new Trainers();
        trainers.setAdminName(trainersDTO.adminName());
        trainers.setAdminPassword(trainersDTO.adminPassword());

        trainersRepository.save(trainers);
    }
    
}
