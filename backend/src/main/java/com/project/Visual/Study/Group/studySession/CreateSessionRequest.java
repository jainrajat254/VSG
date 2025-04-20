package com.project.Visual.Study.Group.studySession;

import java.time.LocalDateTime;

public class CreateSessionRequest {
    private String sessionName;
    private String hostUsername;
    private LocalDateTime endTime;

    public CreateSessionRequest(String sessionName, String hostUsername, LocalDateTime endTime) {
        this.sessionName = sessionName;
        this.hostUsername = hostUsername;
        this.endTime = endTime;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getHostUsername() {
        return hostUsername;
    }

    public void setHostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
