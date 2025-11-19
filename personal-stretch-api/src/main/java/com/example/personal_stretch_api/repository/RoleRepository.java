package com.example.personal_stretch_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.personal_stretch_api.model.Role;

public interface RoleRepository extends JpaRepository<Role,Integer>{
    
}
