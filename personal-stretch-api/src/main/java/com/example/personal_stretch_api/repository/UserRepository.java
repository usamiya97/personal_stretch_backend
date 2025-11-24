package com.example.personal_stretch_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.personal_stretch_api.dto.BookingUserDTO;
import com.example.personal_stretch_api.model.Customers;

@Repository
public interface UserRepository extends JpaRepository<Customers,Long>{
    
    @Query("SELECT new com.example.personal_stretch_api.dto.BookingUserDTO(" +
           "  b.id, " +
           "  c.customerName, " +
           "  c.customerEmail, " +
           "  c.customerPhoneNumber, " +
           "  b.firstChoiceDateTime, " +
           "  b.message, " +
           "  (SELECT COUNT(b2) FROM Booking b2 WHERE b2.customers = c) " + 
           ") " +
           "FROM Booking b " +
           "JOIN b.customers c " +
           "ORDER BY b.firstChoiceDateTime DESC")
        List<BookingUserDTO> findAllBookingUser();
}
