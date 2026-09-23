package com.example.Worker.dto;

import com.example.Worker.enums.JobStatus;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class JobDTO {


    public static class CreateRequest {

        private Long workerId;
        private Long serviceId;
        private String description;
        private MultipartFile image;

        public CreateRequest() {
        }

        public Long getWorkerId() {
            return workerId;
        }

        public void setWorkerId(Long workerId) {
            this.workerId = workerId;
        }

        public Long getServiceId() {
            return serviceId;
        }

        public void setServiceId(Long serviceId) {
            this.serviceId = serviceId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public MultipartFile getImage() {
            return image;
        }

        public void setImage(MultipartFile image) {
            this.image = image;
        }
    }



    public static class Response {

        private Long id;

        private Long customerId;
        private String customerName;

        private Long workerId;
        private String workerName;

        private Long serviceId;
        private String serviceName;

        private String description;
        private String imageUrl;

        private JobStatus status;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Response() {
        }

        public Response(Long id,
                        Long customerId,
                        String customerName,
                        Long workerId,
                        String workerName,
                        Long serviceId,
                        String serviceName,
                        String description,
                        String imageUrl,
                        JobStatus status,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt) {

            this.id = id;
            this.customerId = customerId;
            this.customerName = customerName;
            this.workerId = workerId;
            this.workerName = workerName;
            this.serviceId = serviceId;
            this.serviceName = serviceName;
            this.description = description;
            this.imageUrl = imageUrl;
            this.status = status;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
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

        public Long getServiceId() {
            return serviceId;
        }

        public void setServiceId(Long serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
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

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}