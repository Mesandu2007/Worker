package com.example.Worker.dto;

import java.time.LocalDateTime;

public class ConversationDTO {

    
    public static class Response {

        private Long id;

        private Long jobId;

        private Long customerId;
        private String customerName;

        private Long workerId;
        private String workerName;

        private LocalDateTime createdAt;

        public Response() {
        }

        public Response(Long id,
                        Long jobId,
                        Long customerId,
                        String customerName,
                        Long workerId,
                        String workerName,
                        LocalDateTime createdAt) {

            this.id = id;
            this.jobId = jobId;
            this.customerId = customerId;
            this.customerName = customerName;
            this.workerId = workerId;
            this.workerName = workerName;
            this.createdAt = createdAt;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getJobId() {
            return jobId;
        }

        public void setJobId(Long jobId) {
            this.jobId = jobId;
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

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }
    }
}