package com.example.Worker.service;

import com.example.Worker.dto.AuthDTO;
import com.example.Worker.entity.Customer;
import com.example.Worker.entity.Worker;
import com.example.Worker.enums.Role;
import com.example.Worker.repository.CustomerRepository;
import com.example.Worker.repository.WorkerRepository;
import com.example.Worker.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final WorkerRepository workerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            CustomerRepository customerRepository,
            WorkerRepository workerRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.customerRepository = customerRepository;
        this.workerRepository = workerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    // ==========================================
    // REGISTER CUSTOMER
    // ==========================================

    public AuthDTO.AuthResponse registerCustomer(
            AuthDTO.CustomerRegisterRequest request
    ) {

        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Customer customer = new Customer();

        customer.setEmail(request.getEmail());

        customer.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        customer.setRole(Role.CUSTOMER);

        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setLocation(request.getLocation());

        customerRepository.save(customer);

        String token = jwtService.generateToken(
                customer.getId(),
                customer.getEmail(),
                customer.getRole().name()
        );

        return new AuthDTO.AuthResponse(
                token,
                customer.getId(),
                customer.getEmail(),
                customer.getName(),
                customer.getRole()
        );
    }


    // ==========================================
    // REGISTER WORKER
    // ==========================================

    public AuthDTO.AuthResponse registerWorker(
            AuthDTO.WorkerRegisterRequest request
    ) {

        if (workerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Worker worker = new Worker();

        worker.setEmail(request.getEmail());

        worker.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        worker.setRole(Role.WORKER);

        worker.setName(request.getName());

        // NEW
        worker.setType(request.getType());

        worker.setPhone(request.getPhone());
        worker.setLocation(request.getLocation());
        worker.setDescription(request.getDescription());
        worker.setSkills(request.getSkills());
        worker.setExperience(request.getExperience());
        worker.setHourlyRate(request.getHourlyRate());
        worker.setAvailability(request.getAvailability());

        workerRepository.save(worker);

        String token = jwtService.generateToken(
                worker.getId(),
                worker.getEmail(),
                worker.getRole().name()
        );

        return new AuthDTO.AuthResponse(
                token,
                worker.getId(),
                worker.getEmail(),
                worker.getName(),
                worker.getRole()
        );
    }


    // ==========================================
    // LOGIN CUSTOMER
    // ==========================================

    public AuthDTO.AuthResponse loginCustomer(
            AuthDTO.LoginRequest request
    ) {

        Customer customer = customerRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                customer.getPassword()
        )) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                customer.getId(),
                customer.getEmail(),
                customer.getRole().name()
        );

        return new AuthDTO.AuthResponse(
                token,
                customer.getId(),
                customer.getEmail(),
                customer.getName(),
                customer.getRole()
        );
    }


    // ==========================================
    // LOGIN WORKER
    // ==========================================

    public AuthDTO.AuthResponse loginWorker(
            AuthDTO.LoginRequest request
    ) {

        Worker worker = workerRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                worker.getPassword()
        )) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                worker.getId(),
                worker.getEmail(),
                worker.getRole().name()
        );

        return new AuthDTO.AuthResponse(
                token,
                worker.getId(),
                worker.getEmail(),
                worker.getName(),
                worker.getRole()
        );
    }
}