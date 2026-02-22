package com.example.personal_stretch_api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.personal_stretch_api.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long>{

    List<Booking> findByFirstChoiceDateTimeGreaterThanEqualAndFirstChoiceDateTimeLessThan(LocalDateTime startOfDay,
            LocalDateTime startOfNextDay);

}
