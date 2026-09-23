package com.example.Worker.repository;

import com.example.Worker.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Optional<Conversation> findByJobId(Long jobId);

    List<Conversation> findByCustomerId(Long customerId);

    List<Conversation> findByWorkerId(Long workerId);
}