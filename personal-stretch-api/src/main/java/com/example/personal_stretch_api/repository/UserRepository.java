package com.example.personal_stretch_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.personal_stretch_api.dto.BookingUserDTO;
import com.example.personal_stretch_api.model.Customers;

@Repository
public interface UserRepository extends JpaRepository<Customers,Long>{
    
    @Query("""
        SELECT new com.example.personal_stretch_api.dto.BookingUserDTO(
            c.id,
            c.customerName,
            c.customerEmail,
            c.customerPhoneNumber,
            MAX(b.firstChoiceDateTime),
            c.customerMemo,
            COUNT(b.id)
        )
        FROM Customers c
        LEFT JOIN Booking b
            ON b.customers = c
        GROUP BY
            c.id,
            c.customerName,
            c.customerEmail,
            c.customerPhoneNumber,
            c.customerMemo
        ORDER BY
            MAX(b.firstChoiceDateTime) DESC NULLS LAST
    """)
    List<BookingUserDTO> findAllBookingUser();
}
