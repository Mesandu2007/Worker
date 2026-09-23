package com.example.Worker.repository;

import com.example.Worker.entity.Job;
import com.example.Worker.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByCustomerId(Long customerId);

    List<Job> findByWorkerId(Long workerId);

    List<Job> findByCustomerIdAndStatus(
            Long customerId,
            JobStatus status
    );

    List<Job> findByWorkerIdAndStatus(
            Long workerId,
            JobStatus status
    );
}