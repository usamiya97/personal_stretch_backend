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
        
        MIN(CASE WHEN b.status = 'COMPLETE' THEN b.firstChoiceDateTime ELSE NULL END), 

        MAX(CASE WHEN b.status = 'COMPLETE' THEN b.firstChoiceDateTime ELSE NULL END),

        c.customerMemo,

        SUM(CASE WHEN b.status = 'COMPLETE' THEN 1 ELSE 0 END)
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
            MAX(CASE WHEN b.status = 'COMPLETE' THEN b.firstChoiceDateTime ELSE NULL END) DESC NULLS LAST
    """)
    List<BookingUserDTO> findAllBookingUser();
}
