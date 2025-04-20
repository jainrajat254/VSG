package com.project.Visual.Study.Group.studyGroup.group;

import com.project.Visual.Study.Group.user.User;
import com.project.Visual.Study.Group.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudyGroupService {

    @Autowired
    private  StudyGroupRepository studyGroupRepository;
    @Autowired
    private  SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private  UserRepository userRepository;

    public GroupResponse createGroup(CreateGroupRequest request) {
        User creator = userRepository.findByUsername(request.getCreatedByUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String joiningCode;
        do {
            joiningCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (studyGroupRepository.existsByJoiningCode(joiningCode));

        GroupMember creatorMember = new GroupMember(creator.getId(), creator.getUsername(), creator.getName());
        StudyGroup group = new StudyGroup();
        group.setGroupName(request.getGroupName());
        group.setDescription(request.getDescription());
        group.setCreatedByName(request.getCreatedByName());
        group.setCreatedByUsername(request.getCreatedByUsername());
        group.setCreatedById(creator.getId());
        group.setJoiningCode(joiningCode);
        group.setCreatedAt(LocalDateTime.now());
        group.setMembers(new ArrayList<>(List.of(creatorMember)));

        studyGroupRepository.save(group);
        return mapToResponse(group);
    }

    public GroupResponse joinGroup(String joiningCode, String username) {
        StudyGroup group = studyGroupRepository.findByJoiningCode(joiningCode)
                .orElseThrow(() -> new RuntimeException("Invalid joining code"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (group.getMembers().stream().noneMatch(m -> m.getUserId().equals(user.getId()))) {
            group.getMembers().add(new GroupMember(user.getId(), user.getUsername(), user.getName()));
            studyGroupRepository.save(group);
            notifyUsersOfJoin(group.getId(), user.getUsername());
        }

        return mapToResponse(group);
    }

    public List<GroupResponse> getUserGroups(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<StudyGroup> groups = studyGroupRepository.findByMembersUserId(user.getId());
        return groups.stream().map(this::mapToResponse).toList();
    }

    public List<GroupResponse> getAllGroups() {
        List<StudyGroup> groups = studyGroupRepository.findAll();
        return groups.stream().map(this::mapToResponse).toList();
    }

    public GroupResponse leaveGroup(String username, String sessionId) {
        StudyGroup group = studyGroupRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        group.getMembers().removeIf(m -> m.getUsername().equals(username));
        studyGroupRepository.save(group);

        notifyUsersOfLeave(group.getId(), username);

        return mapToResponse(group);
    }

    private GroupResponse mapToResponse(StudyGroup group) {
        return new GroupResponse(
                group.getId(),
                group.getGroupName(),
                group.getDescription(),
                group.getMembers(),
                group.getCreatedAt()
        );
    }

    private void notifyUsersOfJoin(String groupId, String username) {
        simpMessagingTemplate.convertAndSend("/topic/group/" + groupId, username + " has joined");
    }

    private void notifyUsersOfLeave(String groupId, String username) {
        simpMessagingTemplate.convertAndSend("/topic/group/" + groupId, username + " has left");
    }

}

