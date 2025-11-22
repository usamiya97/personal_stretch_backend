package com.example.personal_stretch_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.personal_stretch_api.dto.DetailBooking;
import com.example.personal_stretch_api.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long>{

    // @Query(value = "UPDATE bookings SET ")
    void updateData(DetailBooking detailBooking);

}
