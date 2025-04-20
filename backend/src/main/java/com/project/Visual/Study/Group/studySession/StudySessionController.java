package com.project.Visual.Study.Group.studySession;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class StudySessionController {

    @Autowired
    private StudySessionService sessionService;

    @PostMapping("/create")
    public ResponseEntity<SessionResponse> createSession(@RequestBody CreateSessionRequest request) {
        return ResponseEntity.ok(sessionService.createSession(request));
    }

    @PostMapping("/join/{username}/{joiningCode}")
    public ResponseEntity<SessionResponse> joinSession(@PathVariable String username, @PathVariable String joiningCode) {
        return ResponseEntity.ok(sessionService.joinSession(username, joiningCode));
    }

    @PostMapping("/leave/{username}/{sessionId}")
    public ResponseEntity<SessionResponse> leaveSession(@PathVariable String username, @PathVariable String sessionId) {
        return ResponseEntity.ok(sessionService.leaveSession(username, sessionId));
    }

    @PostMapping("/complete/{sessionId}/{userId}")
    public ResponseEntity<SessionResponse> completeSession(@PathVariable String sessionId, @PathVariable String userId) {
        return ResponseEntity.ok(sessionService.completeSession(sessionId, userId));
    }

}

