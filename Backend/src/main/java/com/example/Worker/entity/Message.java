package com.example.Worker.entity;

import com.example.Worker.enums.SenderType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    
    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;



    @Column(nullable = false)
    private Long senderId;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SenderType senderType;



    @Column(nullable = false, length = 2000)
    private String content;



    @Column(nullable = false, updatable = false)
    private LocalDateTime sentAt;



    public Message() {
    }



    public Message(Conversation conversation,
                   Long senderId,
                   SenderType senderType,
                   String content) {

        this.conversation = conversation;
        this.senderId = senderId;
        this.senderType = senderType;
        this.content = content;
    }



    @PrePersist
    protected void onCreate() {
        sentAt = LocalDateTime.now();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }


    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }


    public SenderType getSenderType() {
        return senderType;
    }

    public void setSenderType(SenderType senderType) {
        this.senderType = senderType;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}