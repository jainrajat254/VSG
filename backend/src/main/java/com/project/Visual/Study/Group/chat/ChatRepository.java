package com.project.Visual.Study.Group.chat;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderBySendAtAsc(String senderId, String receiverId, String senderId1, String receiverId1);
}
