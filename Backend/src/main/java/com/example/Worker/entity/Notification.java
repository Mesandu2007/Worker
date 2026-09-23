package com.example.Worker.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;



    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String message;



    @Column(nullable = false)
    private boolean isRead = false;


    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public Notification() {
    }

    public Notification(Customer customer,
                        Worker worker,
                        String title,
                        String message) {

        this.customer = customer;
        this.worker = worker;
        this.title = title;
        this.message = message;
        this.isRead = false;
    }



    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }



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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
