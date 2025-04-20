package com.project.Visual.Study.Group.studyGroup.group;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyGroupRepository extends MongoRepository<StudyGroup, String> {

    List<StudyGroup> findByMembersUserId(String userId);

    Optional<StudyGroup> findByJoiningCode(String joiningCode);

    boolean existsByJoiningCode(String joiningCode);
}
