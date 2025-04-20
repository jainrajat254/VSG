package com.project.Visual.Study.Group.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/message")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/{senderId}/{receiverId}")
    public ResponseEntity<List<Chat>> getChatHistory(@PathVariable String senderId, @PathVariable String receiverId) {
        return ResponseEntity.ok(chatService.getChatHistory(senderId, receiverId));
    }

    @MessageMapping("/private-message")
    public void handlePrivateMessage(@Payload ChatRequest request,
                                     @RequestParam(value = "mediaFile", required = false) MultipartFile mediaFile) {
        Chat savedChat = chatService.saveAndSendMessage(request, mediaFile);
    }
}
