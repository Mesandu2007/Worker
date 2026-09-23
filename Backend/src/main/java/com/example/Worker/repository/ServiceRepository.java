package com.example.Worker.repository;


import com.example.Worker.entity.Service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface ServiceRepository extends JpaRepository<Service, Long>{

    Optional<Service> findByName(String name);

    boolean existsByName(String name);

}
