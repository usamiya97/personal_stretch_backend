package com.example.personal_stretch_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.personal_stretch_api.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer>{


}
