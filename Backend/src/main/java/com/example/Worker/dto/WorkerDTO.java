package com.example.Worker.dto;

public class WorkerDTO {

    public static class ProfileResponse {

        private Long id;
        private String email;
        private String name;

        private String location;
        private String description;
        private String skills;
        private Integer experience;
        private Double hourlyRate;
        private String availability;
        private Double rating;

        public ProfileResponse() {
        }

        public ProfileResponse(Long id,
                               String email,
                               String name,
                               String location,
                               String description,
                               String skills,
                               Integer experience,
                               Double hourlyRate,
                               String availability,
                               Double rating) {

            this.id = id;
            this.email = email;
            this.name = name;
            this.location = location;
            this.description = description;
            this.skills = skills;
            this.experience = experience;
            this.hourlyRate = hourlyRate;
            this.availability = availability;
            this.rating = rating;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    }


    public static class UpdateRequest {

        private String name;
        private String phone;
        private String location;
        private String description;
        private String skills;
        private Integer experience;
        private Double hourlyRate;
        private String availability;

        public UpdateRequest() {
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
    }
}