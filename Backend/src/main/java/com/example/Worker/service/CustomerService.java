package com.example.Worker.service;

import com.example.Worker.dto.CustomerDTO;
import com.example.Worker.entity.Customer;
import com.example.Worker.repository.CustomerRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

   

    public CustomerDTO.ProfileResponse getMyProfile() {

        Long customerId = getCurrentCustomerId();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found")
                );

        return new CustomerDTO.ProfileResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getName(),
                customer.getPhone(),
                customer.getLocation()
        );
    }



    public CustomerDTO.ProfileResponse updateMyProfile(
            CustomerDTO.UpdateRequest request
    ) {

        Long customerId = getCurrentCustomerId();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found")
                );

        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setLocation(request.getLocation());

        Customer updatedCustomer = customerRepository.save(customer);

        return new CustomerDTO.ProfileResponse(
                updatedCustomer.getId(),
                updatedCustomer.getEmail(),
                updatedCustomer.getName(),
                updatedCustomer.getPhone(),
                updatedCustomer.getLocation()
        );
    }



    private Long getCurrentCustomerId() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return (Long) authentication.getDetails();
    }
}