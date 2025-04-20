package com.project.Visual.Study.Group.studyGroup.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class GroupResponse {
    private String id;
    private String groupName;
    private String description;
    private List<GroupMember> members;
    private LocalDateTime createdAt;

    public GroupResponse(String id, String groupName, String description, List<GroupMember> members, LocalDateTime createdAt) {
        this.id = id;
        this.groupName = groupName;
        this.description = description;
        this.members = members;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GroupMember> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMember> members) {
        this.members = members;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
