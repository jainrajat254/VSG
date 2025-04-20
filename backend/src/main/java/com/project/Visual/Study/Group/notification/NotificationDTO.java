package com.project.Visual.Study.Group.notification;

public class NotificationDTO {
    private String message;
    private String type;
    private String senderId;
    private String mediaUrl;

    public NotificationDTO(String message, String type, String senderId, String mediaUrl) {
        this.message = message;
        this.type = type;
        this.senderId = senderId;
        this.mediaUrl = mediaUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}

