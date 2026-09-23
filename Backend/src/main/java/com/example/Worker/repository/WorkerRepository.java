package com.example.Worker.repository;

import com.example.Worker.entity.Worker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;




public interface WorkerRepository extends JpaRepository<Worker, Long>{

    Optional<Worker> findByEmail(String email);

    boolean existsByEmail(String email);
}
