package com.example.Worker.service;
import com.example.Worker.dto.AuthDTO;
import com.example.Worker.entity.Worker;
import com.example.householdworker.repository.CustomerRepository;
import com.example.householdworker.repository.WorkerRepository;
import com.example.householdworker.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthService {

    private final CustomerRepository customeRepository;
    private final WorkerRepository workerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Service
    public class AuthService{

         private final CustomerRepository customerRepository;
         private final WorkerRepository workerRepository;
         private final PasswordEncoder passwordEncoder;
         private final JwtService jwtService;




         public AuthService(

            CustomerRepository customerRepository,
            WorkerRepository workerRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService

         ){

            this.customerRepository=customerRepository;
            this.workerRepository = workerRepository;
            this.passwordEncoder = passwordEncoder;
            this.jwtService = jwtService;
    
         }

         public String registerCustomer(AuthDTO request){

            if(customerRepository.existsByEmail(request.getEmail())){

                throw new RuntimeException("Email already registered");

            }

            Customer customer=new Customer();

            customer.setName(request.getName());
            customer.setEmail(request.getEmail());

            customer.setPassword(passwordEncoder.encode(request.getPassword()));

            customerRepository.save(customer);

            return jwtService.generateToken(
                customer.getId(),
                customer.getEmail(),
                "CUSTOMER"
            
            
        
            );
        }


        public String registerWorker(AuthDTO request) {

            if (workerRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already registered");
            }

            Worker worker = new Worker();

            worker.setName(request.getName());
            worker.setEmail(request.getEmail());

            worker.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );

            workerRepository.save(worker);

            return jwtService.generateToken(
                    worker.getId(),
                    worker.getEmail(),
                    "WORKER"
            );
        }

        public String loginCustomer(AuthDTO request) {

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

            return jwtService.generateToken(
                    customer.getId(),
                    customer.getEmail(),
                    "CUSTOMER"
            );
        }



    public String loginWorker(AuthDTO request) {

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

        return jwtService.generateToken(
                worker.getId(),
                worker.getEmail(),
                "WORKER"
        );
    }





         

         






















    }







}
