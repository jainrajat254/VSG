package com.project.Visual.Study.Group.studySession;

import java.time.LocalDateTime;
import java.util.List;

public class SessionResponse {

    private String id;
    private String sessionName;
    private String joiningCode;
    private String sessionLink;
    private String hostId;
    private String hostName;
    private String hostUsername;
    private List<SessionMember> members;
    private SessionStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public SessionResponse(String id, String sessionName, String joiningCode, String sessionLink, String hostId, String hostName, String hostUsername, List<SessionMember> members, SessionStatus status, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.sessionName = sessionName;
        this.joiningCode = joiningCode;
        this.sessionLink = sessionLink;
        this.hostId = hostId;
        this.hostName = hostName;
        this.hostUsername = hostUsername;
        this.members = members;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getJoiningCode() {
        return joiningCode;
    }

    public void setJoiningCode(String joiningCode) {
        this.joiningCode = joiningCode;
    }

    public String getSessionLink() {
        return sessionLink;
    }

    public void setSessionLink(String sessionLink) {
        this.sessionLink = sessionLink;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostUsername() {
        return hostUsername;
    }

    public void setHostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }

    public List<SessionMember> getMembers() {
        return members;
    }

    public void setMembers(List<SessionMember> members) {
        this.members = members;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
