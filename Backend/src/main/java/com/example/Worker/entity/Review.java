package com.example.Worker.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;



    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;



    @OneToOne
    @JoinColumn(name = "job_id", nullable = false, unique = true)
    private Job job;



    @Column(nullable = false)
    private Integer rating;


    @Column(length = 1000)
    private String comment;



    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;



    public Review() {
    }



    public Review(Customer customer,
                  Worker worker,
                  Job job,
                  Integer rating,
                  String comment) {

        this.customer = customer;
        this.worker = worker;
        this.job = job;
        this.rating = rating;
        this.comment = comment;
    }


    // Automatically set creation time
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
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


    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }


    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}