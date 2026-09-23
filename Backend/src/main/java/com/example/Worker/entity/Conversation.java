package com.example.Worker.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name= "job_id", nullable=false,unique=true)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;



    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;



    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public Conversation(){

    }

    public Conversation(Job job, Customer customer, Worker worker){

        this.job=job;
        this.customer=customer;
        this.worker=worker;

    }

    @PrePersist
    protected void onCreate(){
        createdAt=LocalDateTime.now();
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public Job getJob(){
        return job;
    }
    public void setJob(Job job){
        this.job=job;
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


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
