package com.example.personal_stretch_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.personal_stretch_api.dto.BookingUserDTO;
import com.example.personal_stretch_api.model.Customers;
import com.example.personal_stretch_api.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<BookingUserDTO> getUsers() {
        return userRepository.findAllBookingUser();
    }
    
}
