package com.example.Worker.entity;

import com.example.Worker.enums.JobStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Customer who requested the job
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    // Worker selected for the job
    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;


    // Service requested
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;


    @Column(length = 1000)
    private String description;


    // Optional image of the problem
    private String imageUrl;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status = JobStatus.PENDING;


    // When the job was requested
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    // When the worker accepts the job
    private LocalDateTime acceptedAt;


    // When the worker starts the job
    private LocalDateTime startedAt;


    // When the worker completes the job
    private LocalDateTime completedAt;


    // Default constructor required by JPA
    public Job() {
    }


    // Constructor
    public Job(Customer customer,
               Worker worker,
               Service service,
               String description,
               String imageUrl) {

        this.customer = customer;
        this.worker = worker;
        this.service = service;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = JobStatus.PENDING;
    }


    // Automatically set creation time
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();

        if (status == null) {
            status = JobStatus.PENDING;
        }
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(LocalDateTime acceptedAt) {
        this.acceptedAt = acceptedAt;
    }


    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }


    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}