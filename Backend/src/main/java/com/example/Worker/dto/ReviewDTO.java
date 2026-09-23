package com.example.Worker.dto;

import java.time.LocalDateTime;

public class ReviewDTO {


    public static class Request {

        private Long jobId;
        private Integer rating;
        private String comment;

        public Request() {
        }

        public Long getJobId() {
            return jobId;
        }

        public void setJobId(Long jobId) {
            this.jobId = jobId;
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
    }


    // Used when returning a review
    public static class Response {

        private Long id;

        private Long customerId;
        private String customerName;

        private Long workerId;
        private String workerName;

        private Long jobId;

        private Integer rating;
        private String comment;

        private LocalDateTime createdAt;

        public Response() {
        }

        public Response(Long id,
                        Long customerId,
                        String customerName,
                        Long workerId,
                        String workerName,
                        Long jobId,
                        Integer rating,
                        String comment,
                        LocalDateTime createdAt) {

            this.id = id;
            this.customerId = customerId;
            this.customerName = customerName;
            this.workerId = workerId;
            this.workerName = workerName;
            this.jobId = jobId;
            this.rating = rating;
            this.comment = comment;
            this.createdAt = createdAt;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public Long getWorkerId() {
            return workerId;
        }

        public void setWorkerId(Long workerId) {
            this.workerId = workerId;
        }

        public String getWorkerName() {
            return workerName;
        }

        public void setWorkerName(String workerName) {
            this.workerName = workerName;
        }

        public Long getJobId() {
            return jobId;
        }

        public void setJobId(Long jobId) {
            this.jobId = jobId;
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
}