package com.project.Visual.Study.Group.studyGroup.message;

import com.project.Visual.Study.Group.cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/group-chat")
public class GroupMessageController {

    @Autowired
    private  GroupMessageService groupMessageService;
    @Autowired
    private  CloudinaryService cloudinaryService;
    @Autowired
    private  SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/group-chat/{groupId}")
    public void handleGroupMessage(@DestinationVariable String groupId, GroupMessageRequest message, @RequestParam(value = "mediaFile", required = false) MultipartFile mediaFile) {
        String mediaUrl = null;
        if (mediaFile != null && !mediaFile.isEmpty()) {
            mediaUrl = cloudinaryService.uploadFile(mediaFile);
        }
        GroupMessage savedMessage = groupMessageService.saveMessage(groupId, message, mediaUrl);
        simpMessagingTemplate.convertAndSend("/topic/group-chat/" + groupId, savedMessage);
    }


    @GetMapping("/{groupId}")
    public ResponseEntity<List<GroupMessage>> getChatHistory(@PathVariable String groupId) {
        return ResponseEntity.ok(groupMessageService.getChatHistory(groupId));
    }
}

