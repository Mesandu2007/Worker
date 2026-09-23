package com.example.Worker.entity;
import com.example.Worker.enums.Role;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable=false,unique=true)
    private String email;


    @Column(nullable=false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role=Role.WORKER;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String phone;

    private String location;

    @Column(length=1000)
    private String description;

    private String skills;

    private Integer experience;

    private Double hourlyRate;

    private String availability;

    private Double rating = 0.0;

    @Column(nullable=false, updatable=false)
    private LocalDateTime createdAt;

    public Worker(String email,
                  String password,
                  Role role,
                  String name,
                  String phone,
                  String location,
                  String description,
                  String skills,
                  Integer experience,
                  Double hourlyRate,
                  String availability

    ){
        this.email=email;
        this.password=password;
        this.role=role;
        this.name=name;
        this.phone = phone;
        this.location = location;
        this.description = description;
        this.skills = skills;
        this.experience = experience;
        this.hourlyRate = hourlyRate;
        this.availability = availability;


    }

    @PrePersist
    protected void onCreate(){

        createdAt=LocalDateTime.now();

        if(rating == null){
            rating=0.0;
        }
    }


    public Long getId(){
        return id;

    }
    public  void setId(Long id){
        this.id=id;

    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }


    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }


    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }


    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }


    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}
