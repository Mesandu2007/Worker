package com.example.Worker.service;

import com.example.Worker.dto.WorkerDTO;
import com.example.Worker.entity.Worker;
import com.example.Worker.repository.WorkerRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }


  

    public WorkerDTO.ProfileResponse getMyProfile() {

        Long workerId = getCurrentWorkerId();

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new RuntimeException("Worker not found")
                );

        return convertToProfileResponse(worker);
    }


  

    public WorkerDTO.ProfileResponse updateMyProfile(
            WorkerDTO.UpdateRequest request
    ) {

        Long workerId = getCurrentWorkerId();

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new RuntimeException("Worker not found")
                );

        worker.setName(request.getName());
        worker.setType(request.getType());
        worker.setPhone(request.getPhone());
        worker.setLocation(request.getLocation());
        worker.setDescription(request.getDescription());
        worker.setSkills(request.getSkills());
        worker.setExperience(request.getExperience());
        worker.setHourlyRate(request.getHourlyRate());
        worker.setAvailability(request.getAvailability());

        Worker updatedWorker = workerRepository.save(worker);

        return convertToProfileResponse(updatedWorker);
    }



    public List<WorkerDTO.ProfileResponse> searchWorkers(
            String type,
            String location
    ) {

        List<Worker> workers;

        
        if (type != null && !type.isBlank()
                && location != null && !location.isBlank()) {

            workers =
                    workerRepository
                            .findByTypeIgnoreCaseAndLocationIgnoreCase(
                                    type,
                                    location
                            );
        }


        else if (type != null && !type.isBlank()) {

            workers =
                    workerRepository
                            .findByTypeIgnoreCase(type);
        }

        
        else if (location != null && !location.isBlank()) {

            workers =
                    workerRepository
                            .findByLocationIgnoreCase(location);
        }

        
        else {
            throw new RuntimeException("Please provide either type or location for search");
        }

        return workers.stream()
                .map(this::convertToProfileResponse)
                .collect(Collectors.toList());
    }




    private WorkerDTO.ProfileResponse convertToProfileResponse(
            Worker worker
    ) {

        return new WorkerDTO.ProfileResponse(
                worker.getId(),
                worker.getEmail(),
                worker.getName(),
                worker.getType(),
                worker.getLocation(),
                worker.getDescription(),
                worker.getSkills(),
                worker.getExperience(),
                worker.getHourlyRate(),
                worker.getAvailability(),
                worker.getRating()
        );
    }



    private Long getCurrentWorkerId() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return (Long) authentication.getDetails();
    }
}