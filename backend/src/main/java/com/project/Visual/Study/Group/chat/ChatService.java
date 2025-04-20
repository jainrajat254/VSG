package com.project.Visual.Study.Group.chat;

import com.project.Visual.Study.Group.cloudinary.CloudinaryService;
import com.project.Visual.Study.Group.enums.MessageType;
import com.project.Visual.Study.Group.notification.NotificationDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatService {

    @Autowired
    private  ChatRepository repository;
    @Autowired
    private  CloudinaryService cloudinaryService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Chat saveAndSendMessage(ChatRequest request, MultipartFile mediaFile) {
        String mediaUrl = null;
        if (mediaFile != null && !mediaFile.isEmpty()) {
            mediaUrl = cloudinaryService.uploadFile(mediaFile);
        }

        Chat msg = new Chat();
        msg.setSenderId(request.getSenderId());
        msg.setSenderName(request.getSenderName());
        msg.setReceiverId(request.getReceiverId());
        msg.setReceiverName(request.getReceiverName());
        msg.setContent(mediaUrl != null ? mediaUrl : request.getContent());
        msg.setType(mediaFile != null ? MessageType.MEDIA : MessageType.TEXT);
        msg.setSendAt(LocalDateTime.now());

        Chat saved = repository.save(msg);

        messagingTemplate.convertAndSendToUser(
                request.getReceiverId(), "/queue/messages",
                new NotificationDTO(request.getSenderName() + " sent a message",
                        mediaFile != null ? "MEDIA" : "MESSAGE",
                        request.getSenderId(), mediaUrl)  // Pass the media URL if it's a media message
        );

        messagingTemplate.convertAndSendToUser(
                request.getSenderId(), "/queue/messages", saved
        );

        return saved;
    }


    public List<Chat> getChatHistory(String senderId, String receiverId) {
        return repository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderBySendAtAsc(
                senderId, receiverId, senderId, receiverId
        );
    }
}

