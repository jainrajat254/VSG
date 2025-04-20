package com.project.Visual.Study.Group.studyGroup.message;

import com.project.Visual.Study.Group.enums.MessageType;

public class GroupMessageRequest {

    private String senderId;
    private String senderUsername;
    private String content;
    private MessageType type;

    public GroupMessageRequest(String senderId, String senderUsername, String content, MessageType type) {
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.content = content;
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
