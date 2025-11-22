package com.example.personal_stretch_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.personal_stretch_api.model.Customers;

@Repository
public interface CustomerRepository extends JpaRepository<Customers,Long>{
    
}
