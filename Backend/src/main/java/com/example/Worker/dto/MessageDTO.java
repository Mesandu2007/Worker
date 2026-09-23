package com.example.Worker.dto;

import com.example.Worker.enums.SenderType;

import java.time.LocalDateTime;

public class MessageDTO {

    
    public static class SendRequest {

        private Long conversationId;
        private String content;

        public SendRequest() {
        }

        public Long getConversationId() {
            return conversationId;
        }

        public void setConversationId(Long conversationId) {
            this.conversationId = conversationId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    // Used when returning a message
    public static class Response {

        private Long id;

        private Long conversationId;

        private Long senderId;

        private SenderType senderType;

        private String content;

        private LocalDateTime sentAt;

        public Response() {
        }

        public Response(Long id,
                        Long conversationId,
                        Long senderId,
                        SenderType senderType,
                        String content,
                        LocalDateTime sentAt) {

            this.id = id;
            this.conversationId = conversationId;
            this.senderId = senderId;
            this.senderType = senderType;
            this.content = content;
            this.sentAt = sentAt;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getConversationId() {
            return conversationId;
        }

        public void setConversationId(Long conversationId) {
            this.conversationId = conversationId;
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
}
