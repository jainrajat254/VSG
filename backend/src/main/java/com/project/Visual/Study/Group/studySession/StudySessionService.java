package com.project.Visual.Study.Group.studySession;

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
public class StudySessionService {

    @Autowired
    private  StudySessionRepository studySessionRepository;
    @Autowired
    private  SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private  UserRepository userRepository;


    public SessionResponse createSession(CreateSessionRequest request) {

        User host = userRepository.findByUsername(request.getHostUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String joiningCode;
        do {
            joiningCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (studySessionRepository.existsByJoiningCode(joiningCode));
        String sessionLink = "https://vsg.app/session/" + joiningCode;

        SessionMember hostMember = new SessionMember(host.getId(), host.getName(), host.getUsername());
        List<SessionMember> members = new ArrayList<>();
        members.add(hostMember);

        StudySession session = new StudySession();
        session.setSessionName(request.getSessionName());
        session.setHostId(host.getId());
        session.setHostName(host.getName());
        session.setHostUsername(host.getUsername());
        session.setJoiningCode(joiningCode);
        session.setSessionLink(sessionLink);
        session.setMembers(members);
        session.setStatus(SessionStatus.ONGOING);
        session.setStartTime(LocalDateTime.now());
        session.setEndTime(request.getEndTime());

        studySessionRepository.save(session);

        return mapToResponse(session);
    }

    public SessionResponse joinSession(String username, String joiningCode) {
        StudySession session = studySessionRepository.findByJoiningCode(joiningCode)
                .orElseThrow(() -> new RuntimeException("Invalid joining code"));

        if (session.getStatus() == SessionStatus.COMPLETED) {
            throw new RuntimeException("Session Completed");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SessionMember member = new SessionMember(user.getId(), user.getUsername(), user.getName());

        if (session.getMembers().stream().noneMatch(m -> m.getId().equals(user.getId()))) {
            session.getMembers().add(member);
            studySessionRepository.save(session);

            notifyUsersOfJoin(session.getId(), user.getUsername());
        } else {
            throw new RuntimeException("User is already a member of the session.");
        }

        return mapToResponse(session);
    }

    public SessionResponse leaveSession(String username, String sessionId) {
        StudySession session = studySessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.getMembers().removeIf(m -> m.getUsername().equals(username));
        studySessionRepository.save(session);

        notifyUsersOfLeave(session.getId(), username);

        return mapToResponse(session);
    }

    private SessionResponse mapToResponse(StudySession session) {
        return new SessionResponse(
                session.getId(),
                session.getSessionName(),
                session.getJoiningCode(),
                session.getSessionLink(),
                session.getHostId(),
                session.getHostName(),
                session.getHostUsername(),
                session.getMembers(),
                session.getStatus(),
                session.getStartTime(),
                session.getEndTime()
        );
    }

    public SessionResponse completeSession(String sessionId, String userId) {
        StudySession session = studySessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (!session.getHostId().equals(userId)) {
            throw new RuntimeException("Only the host can complete the session");
        }

        session.setStatus(SessionStatus.COMPLETED);
        studySessionRepository.save(session);

        return mapToResponse(session);
    }

    private void notifyUsersOfJoin(String sessionId, String username) {
        simpMessagingTemplate.convertAndSend("/topic/session/" + sessionId, username + " has joined");
    }

    private void notifyUsersOfLeave(String sessionId, String username) {
        simpMessagingTemplate.convertAndSend("/topic/session/" + sessionId, username + " has left");
    }
}
