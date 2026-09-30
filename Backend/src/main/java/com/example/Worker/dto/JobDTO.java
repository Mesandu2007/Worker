package com.example.Worker.service;

import com.example.Worker.dto.JobDTO;
import com.example.Worker.entity.Customer;
import com.example.Worker.entity.Job;
import com.example.Worker.entity.Service;
import com.example.Worker.entity.Worker;
import com.example.Worker.enums.JobStatus;
import com.example.Worker.repository.CustomerRepository;
import com.example.Worker.repository.JobRepository;
import com.example.Worker.repository.ServiceRepository;
import com.example.Worker.repository.WorkerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class JobService {

    private final JobRepository jobRepository;
    private final CustomerRepository customerRepository;
    private final WorkerRepository workerRepository;
    private final ServiceRepository serviceRepository;
    private final CloudinaryService cloudinaryService;
    private final NotificationService notificationService;

    public JobService(
            JobRepository jobRepository,
            CustomerRepository customerRepository,
            WorkerRepository workerRepository,
            ServiceRepository serviceRepository,
            CloudinaryService cloudinaryService,
            NotificationService notificationService
    ) {
        this.jobRepository = jobRepository;
        this.customerRepository = customerRepository;
        this.workerRepository = workerRepository;
        this.serviceRepository = serviceRepository;
        this.cloudinaryService = cloudinaryService;
        this.notificationService = notificationService;
    }

    // =========================================================
    // CREATE JOB
    // =========================================================

    public JobDTO.Response createJob(JobDTO.CreateRequest request) {

        Long customerId = getCurrentUserId();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Worker worker = workerRepository.findById(request.getWorkerId())
                .orElseThrow(() ->
                        new RuntimeException("Worker not found"));

        Service service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() ->
                        new RuntimeException("Service not found"));

        String imageUrl = null;

        if (request.getImage() != null &&
                !request.getImage().isEmpty()) {

            imageUrl = cloudinaryService.uploadImage(
                    request.getImage()
            );
        }

        Job job = new Job(
                customer,
                worker,
                service,
                request.getDescription(),
                imageUrl
        );

        job.setStatus(JobStatus.PENDING);

        Job savedJob = jobRepository.save(job);

        // Notify selected worker
        notificationService.createNotification(
                null,
                worker,
                "New Job Request",
                "You have received a new job request from "
                        + customer.getName()
        );

        return convertToResponse(savedJob);
    }

    // =========================================================
    // GET CUSTOMER'S JOBS
    // =========================================================

    public List<JobDTO.Response> getMyJobs() {

        Long customerId = getCurrentUserId();

        List<Job> jobs =
                jobRepository.findByCustomerId(customerId);

        return jobs.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // =========================================================
    // GET WORKER'S RECEIVED JOBS
    // =========================================================

    public List<JobDTO.Response> getReceivedJobs() {

        Long workerId = getCurrentUserId();

        List<Job> jobs =
                jobRepository.findByWorkerId(workerId);

        return jobs.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // =========================================================
    // ACCEPT JOB
    // =========================================================

    public JobDTO.Response acceptJob(Long jobId) {

        Long workerId = getCurrentUserId();

        Job job = getJob(jobId);

        // Check worker owns this job request
        if (!job.getWorker().getId().equals(workerId)) {
            throw new RuntimeException(
                    "You are not authorized to accept this job"
            );
        }

        // Only pending jobs can be accepted
        if (job.getStatus() != JobStatus.PENDING) {
            throw new RuntimeException(
                    "Only pending jobs can be accepted"
            );
        }

        job.setStatus(JobStatus.ACCEPTED);
        job.setAcceptedAt(LocalDateTime.now());

        Job updatedJob = jobRepository.save(job);

        // Notify customer
        notificationService.createNotification(
                job.getCustomer(),
                null,
                "Job Accepted",
                "Your job request has been accepted by "
                        + job.getWorker().getName()
        );

        return convertToResponse(updatedJob);
    }

    // =========================================================
    // REJECT JOB
    // =========================================================

    public JobDTO.Response rejectJob(Long jobId) {

        Long workerId = getCurrentUserId();

        Job job = getJob(jobId);

        // Check worker owns this job request
        if (!job.getWorker().getId().equals(workerId)) {
            throw new RuntimeException(
                    "You are not authorized to reject this job"
            );
        }

        // Only pending jobs can be rejected
        if (job.getStatus() != JobStatus.PENDING) {
            throw new RuntimeException(
                    "Only pending jobs can be rejected"
            );
        }

        job.setStatus(JobStatus.REJECTED);

        Job updatedJob = jobRepository.save(job);

        // Notify customer
        notificationService.createNotification(
                job.getCustomer(),
                null,
                "Job Rejected",
                "Your job request has been rejected by "
                        + job.getWorker().getName()
        );

        return convertToResponse(updatedJob);
    }

    // =========================================================
    // CANCEL JOB
    // =========================================================

    public JobDTO.Response cancelJob(Long jobId) {

        Long userId = getCurrentUserId();

        Job job = getJob(jobId);

        boolean isCustomer =
                job.getCustomer().getId().equals(userId);

        boolean isWorker =
                job.getWorker().getId().equals(userId);

        // User must be one of the participants
        if (!isCustomer && !isWorker) {
            throw new RuntimeException(
                    "You are not authorized to cancel this job"
            );
        }

        // Only accepted jobs can be cancelled
        if (job.getStatus() != JobStatus.ACCEPTED) {
            throw new RuntimeException(
                    "Only accepted jobs can be cancelled"
            );
        }

        job.setStatus(JobStatus.CANCELLED);

        Job updatedJob = jobRepository.save(job);

        // Customer cancelled
        if (isCustomer) {

            notificationService.createNotification(
                    null,
                    job.getWorker(),
                    "Job Cancelled",
                    "The customer has cancelled the job"
            );

        }

        // Worker cancelled
        else {

            notificationService.createNotification(
                    job.getCustomer(),
                    null,
                    "Job Cancelled",
                    "The worker has cancelled the job"
            );
        }

        return convertToResponse(updatedJob);
    }

    // =========================================================
    // START JOB
    // =========================================================

    public JobDTO.Response startJob(Long jobId) {

        Long workerId = getCurrentUserId();

        Job job = getJob(jobId);

        // Only assigned worker can start
        if (!job.getWorker().getId().equals(workerId)) {
            throw new RuntimeException(
                    "You are not authorized to start this job"
            );
        }

        // Job must be accepted first
        if (job.getStatus() != JobStatus.ACCEPTED) {
            throw new RuntimeException(
                    "Only accepted jobs can be started"
            );
        }

        job.setStatus(JobStatus.IN_PROGRESS);
        job.setStartedAt(LocalDateTime.now());

        Job updatedJob = jobRepository.save(job);

        // Notify customer
        notificationService.createNotification(
                job.getCustomer(),
                null,
                "Job Started",
                "The worker has started working on your job"
        );

        return convertToResponse(updatedJob);
    }

    // =========================================================
    // COMPLETE JOB
    // =========================================================

    public JobDTO.Response completeJob(Long jobId) {

        Long workerId = getCurrentUserId();

        Job job = getJob(jobId);

        // Only assigned worker can complete
        if (!job.getWorker().getId().equals(workerId)) {
            throw new RuntimeException(
                    "You are not authorized to complete this job"
            );
        }

        // Job must be in progress
        if (job.getStatus() != JobStatus.IN_PROGRESS) {
            throw new RuntimeException(
                    "Only jobs in progress can be completed"
            );
        }

        job.setStatus(JobStatus.COMPLETED);
        job.setCompletedAt(LocalDateTime.now());

        Job updatedJob = jobRepository.save(job);

        // Notify customer
        notificationService.createNotification(
                job.getCustomer(),
                null,
                "Job Completed",
                "The worker has completed your job"
        );

        return convertToResponse(updatedJob);
    }

    // =========================================================
    // FIND JOB
    // =========================================================

    private Job getJob(Long jobId) {

        return jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));
    }

    // =========================================================
    // CONVERT ENTITY → DTO
    // =========================================================

    private JobDTO.Response convertToResponse(Job job) {

        return new JobDTO.Response(
                job.getId(),

                job.getCustomer().getId(),
                job.getCustomer().getName(),

                job.getWorker().getId(),
                job.getWorker().getName(),

                job.getService().getId(),
                job.getService().getName(),

                job.getDescription(),
                job.getImageUrl(),

                job.getStatus(),

                job.getCreatedAt(),
                job.getUpdatedAt()
        );
    }

    // =========================================================
    // GET CURRENT USER ID
    // =========================================================

    private Long getCurrentUserId() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return (Long) authentication.getDetails();
    }
}