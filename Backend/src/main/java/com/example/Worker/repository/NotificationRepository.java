package com.example.Worker.repository;

import com.example.Worker.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByCustomerId(Long customerId);

    List<Notification> findByWorkerId(Long workerId);

    List<Notification> findByCustomerIdAndIsReadFalse(Long customerId);

    List<Notification> findByWorkerIdAndIsReadFalse(Long workerId);
}