package com.furiafan.chat.controller;

import com.furiafan.chat.model.FanProfile;
import com.furiafan.chat.model.Message;
import com.furiafan.chat.service.ChatService;
import com.furiafan.chat.service.OpenAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {

    private final ChatService chatService;
    private final OpenAIService openAIService;

    public ChatController(ChatService chatService, OpenAIService openAIService) {
        this.chatService = chatService;
        this.openAIService = openAIService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
        List<Message> messages = chatService.getAllMessages();
        return ResponseEntity.ok(messages);
    }
    @GetMapping("/profile")
    public ResponseEntity<FanProfile> getProfile() {
        return ResponseEntity.ok(chatService.getFanProfile());
    }


    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody Map<String, String> request) {
        String messageContent = request.get("message");
        Message userMessage = new Message("Usuário", messageContent);
        chatService.processMessage(userMessage);
        return ResponseEntity.ok().build();
    }



    @PostMapping("/upload-document")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhum arquivo enviado.");
        }

        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.endsWith(".pdf")) {
            return ResponseEntity.ok("✅ Documento recebido e validado com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("⚠️ Documento inválido. Envie um PDF.");
        }
    }
}
