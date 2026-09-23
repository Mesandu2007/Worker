package com.example.Worker.repository;

import com.example.Worker.entity.Review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long>{


    List<Review> findByWorkerId(Long workerId);

    List<Review> findByCustomerId(Long customerId);

    boolean existsByJobId(Long jobId);
}
