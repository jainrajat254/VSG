package com.project.Visual.Study.Group.studyGroup.group;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class StudyGroupController {

    @Autowired
    private StudyGroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<GroupResponse> create(@RequestBody CreateGroupRequest request) {
        return ResponseEntity.ok(groupService.createGroup(request));
    }

    @PostMapping("/join/{joiningCode}/{username}")
    public ResponseEntity<GroupResponse> join(@PathVariable String joiningCode, @PathVariable String username) {
        return ResponseEntity.ok(groupService.joinGroup(joiningCode, username));
    }

    @PostMapping("/leave/{username}/{sessionId}")
    public ResponseEntity<GroupResponse> leaveGroup(@PathVariable String username, @PathVariable String sessionId) {
        return ResponseEntity.ok(groupService.leaveGroup(username, sessionId));
    }

    @GetMapping("/all-groups")
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<GroupResponse>> getUserGroups(@PathVariable String username) {
        return ResponseEntity.ok(groupService.getUserGroups(username));
    }

}

