```java
package com.example.Worker.service;

import com.example.Worker.dto.NotificationDTO;
import com.example.Worker.entity.Customer;
import com.example.Worker.entity.Notification;
import com.example.Worker.entity.Worker;
import com.example.Worker.repository.NotificationRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(
            NotificationRepository notificationRepository
    ) {
        this.notificationRepository = notificationRepository;
    }




    public NotificationDTO.Response createNotification(
            Customer customer,
            Worker worker,
            String title,
            String message
    ) {

        if (customer == null && worker == null) {
            throw new RuntimeException(
                    "Notification must have a recipient"
            );
        }

        if (customer != null && worker != null) {
            throw new RuntimeException(
                    "Notification cannot have two recipients"
            );
        }

        Notification notification =
                new Notification(
                        customer,
                        worker,
                        title,
                        message
                );

        notification.setRead(false);

        Notification savedNotification =
                notificationRepository.save(notification);

        return convertToResponse(savedNotification);
    }


  

    public List<NotificationDTO.Response> getMyNotifications() {

        Long userId = getCurrentUserId();

        String role = getCurrentUserRole();

        List<Notification> notifications;

        if (role.equals("ROLE_CUSTOMER")) {

            notifications =
                    notificationRepository
                            .findByCustomerId(userId);

        } else if (role.equals("ROLE_WORKER")) {

            notifications =
                    notificationRepository
                            .findByWorkerId(userId);

        } else {

            throw new RuntimeException(
                    "Invalid user role"
            );
        }

        return notifications.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


  

    public NotificationDTO.Response markAsRead(
            Long notificationId
    ) {

        Long userId = getCurrentUserId();

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                )
                        );

        boolean isCustomer =
                notification.getCustomer() != null &&
                notification.getCustomer()
                        .getId()
                        .equals(userId);

        boolean isWorker =
                notification.getWorker() != null &&
                notification.getWorker()
                        .getId()
                        .equals(userId);

        if (!isCustomer && !isWorker) {

            throw new RuntimeException(
                    "You are not authorized to update this notification"
            );
        }

        notification.setRead(true);

        Notification updatedNotification =
                notificationRepository.save(notification);

        return convertToResponse(updatedNotification);
    }




    public void deleteNotification(
            Long notificationId
    ) {

        Long userId = getCurrentUserId();

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                )
                        );

        boolean isCustomer =
                notification.getCustomer() != null &&
                notification.getCustomer()
                        .getId()
                        .equals(userId);

        boolean isWorker =
                notification.getWorker() != null &&
                notification.getWorker()
                        .getId()
                        .equals(userId);

        if (!isCustomer && !isWorker) {

            throw new RuntimeException(
                    "You are not authorized to delete this notification"
            );
        }

        notificationRepository.delete(notification);
    }




    private NotificationDTO.Response convertToResponse(
            Notification notification
    ) {

        return new NotificationDTO.Response(

                notification.getId(),

                notification.getTitle(),

                notification.getMessage(),

                notification.isRead(),

                notification.getCreatedAt()
        );
    }



    private Long getCurrentUserId() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return (Long) authentication.getDetails();
    }




    private String getCurrentUserRole() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();
    }
}


