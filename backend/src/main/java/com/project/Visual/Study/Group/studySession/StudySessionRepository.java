package com.project.Visual.Study.Group.studySession;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudySessionRepository extends MongoRepository<StudySession, String> {

    Optional<StudySession> findByJoiningCode(String joiningCode);

    boolean existsByJoiningCode(String joiningCode);
}
