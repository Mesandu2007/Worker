package com.example.Worker.service;

import com.example.Worker.dto.ConversationDTO;
import com.example.Worker.entity.Conversation;
import com.example.Worker.entity.Customer;
import com.example.Worker.entity.Job;
import com.example.Worker.entity.Worker;
import com.example.Worker.repository.ConversationRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationService(
            ConversationRepository conversationRepository
    ) {
        this.conversationRepository = conversationRepository;
    }



    public ConversationDTO.Response createConversation(
            Job job,
            Customer customer,
            Worker worker
    ) {

        // Make sure a conversation does not already exist
        if (conversationRepository.findByJobId(job.getId()).isPresent()) {

            throw new RuntimeException(
                    "Conversation already exists for this job"
            );
        }

        Conversation conversation =
                new Conversation(
                        job,
                        customer,
                        worker
                );

        Conversation savedConversation =
                conversationRepository.save(conversation);

        return convertToResponse(savedConversation);
    }




    public List<ConversationDTO.Response> getMyConversations() {

        Long userId = getCurrentUserId();

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        List<Conversation> conversations;

        if (role.equals("ROLE_CUSTOMER")) {

            conversations =
                    conversationRepository
                            .findByCustomerId(userId);

        } else if (role.equals("ROLE_WORKER")) {

            conversations =
                    conversationRepository
                            .findByWorkerId(userId);

        } else {

            throw new RuntimeException(
                    "Invalid user role"
            );
        }

        return conversations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }




    public ConversationDTO.Response getConversation(
            Long conversationId
    ) {

        Long userId = getCurrentUserId();

        Conversation conversation =
                conversationRepository.findById(conversationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Conversation not found"
                                )
                        );

        boolean isCustomer =
                conversation.getCustomer()
                        .getId()
                        .equals(userId);

        boolean isWorker =
                conversation.getWorker()
                        .getId()
                        .equals(userId);

        if (!isCustomer && !isWorker) {

            throw new RuntimeException(
                    "You are not authorized to view this conversation"
            );
        }

        return convertToResponse(conversation);
    }




    public void deleteConversation(
            Long conversationId
    ) {

        Long userId = getCurrentUserId();

        Conversation conversation =
                conversationRepository.findById(conversationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Conversation not found"
                                )
                        );

        boolean isCustomer =
                conversation.getCustomer()
                        .getId()
                        .equals(userId);

        boolean isWorker =
                conversation.getWorker()
                        .getId()
                        .equals(userId);

        if (!isCustomer && !isWorker) {

            throw new RuntimeException(
                    "You are not authorized to delete this conversation"
            );
        }

        conversationRepository.delete(conversation);
    }




    private ConversationDTO.Response convertToResponse(
            Conversation conversation
    ) {

        return new ConversationDTO.Response(

                conversation.getId(),

                conversation.getJob().getId(),

                conversation.getCustomer().getId(),
                conversation.getCustomer().getName(),

                conversation.getWorker().getId(),
                conversation.getWorker().getName(),

                conversation.getCreatedAt()
        );
    }




    private Long getCurrentUserId() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return (Long) authentication.getDetails();
    }
}