package com.example.personal_stretch_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.personal_stretch_api.dto.BookingUserDTO;
import com.example.personal_stretch_api.dto.CustomerDTO;
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

    public void setCustomer(CustomerDTO customerDTO) {
        userRepository.save(createCustomer(customerDTO));
    }

    private Customers createCustomer(CustomerDTO customerDTO) {
        Customers customers = new Customers();
        LocalDateTime now = LocalDateTime.now();

        if (customerDTO.id() != null) {
            customers.setId(customerDTO.id());
        }

        customers.setCustomerName(customerDTO.name());
        customers.setCustomerEmail(customerDTO.email());
        customers.setCustomerPhoneNumber(customerDTO.phone());
        customers.setCustomerMemo(customerDTO.memo());
        customers.setCreatedAt(now);

        return customers;
    }

    public void updateCustomer(CustomerDTO customerDTO) {
        userRepository.save(createCustomer(customerDTO));
    }
}
