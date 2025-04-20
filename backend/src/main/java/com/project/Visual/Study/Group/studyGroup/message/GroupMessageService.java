package com.project.Visual.Study.Group.studyGroup.message;

import com.project.Visual.Study.Group.enums.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupMessageService {

    @Autowired
    private  GroupMessageRepository groupMessageRepository;
    @Autowired
    private  SimpMessagingTemplate simpMessagingTemplate;

    public GroupMessage saveMessage(String groupId, GroupMessageRequest request, String mediaUrl) {

        GroupMessage message = new GroupMessage();
        message.setGroupId(groupId);
        message.setSenderId(request.getSenderId());
        message.setSenderUsername(request.getSenderUsername());
        message.setContent(mediaUrl != null ? mediaUrl : request.getContent());
        message.setType(mediaUrl != null ? MessageType.MEDIA : MessageType.TEXT);

        GroupMessage savedMessage = groupMessageRepository.save(message);

        notifyUsersOfMessage(savedMessage.getGroupId(), savedMessage.getSenderUsername());

        return savedMessage;
    }


    public List<GroupMessage> getChatHistory(String groupId) {
        return groupMessageRepository.findByGroupIdOrderByTimestampAsc(groupId);
    }

    private void notifyUsersOfMessage(String groupId, String username) {
        simpMessagingTemplate.convertAndSend("/topic/group/" + groupId, username + " sent a message");
    }
}
