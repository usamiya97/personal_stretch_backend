package com.example.personal_stretch_api.repository;

import com.example.personal_stretch_api.model.Trainers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainersRepository extends JpaRepository<Trainers,Long>{
    public Optional<Trainers> findByAdminName(String adminName);
}
