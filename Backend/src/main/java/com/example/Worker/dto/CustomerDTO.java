package com.example.Worker.dto;

public class CustomerDTO {

    public static class ProfileResponse {

        private Long id;
        private String email;
        private String name;
        private String phone;
        private String location;

        public ProfileResponse() {
        }

        public ProfileResponse(Long id,
                               String email,
                               String name,
                               String phone,
                               String location) {
            this.id = id;
            this.email = email;
            this.name = name;
            this.phone = phone;
            this.location = location;
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
    }


    public static class UpdateRequest {

        private String name;
        private String phone;
        private String location;

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
    }
}
