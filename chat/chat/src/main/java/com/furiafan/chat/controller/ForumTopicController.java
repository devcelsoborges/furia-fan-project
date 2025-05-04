package com.furiafan.chat.controller;

import com.furiafan.chat.entity.ForumTopic;
import com.furiafan.chat.service.ForumTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ForumTopicController {

    @Autowired
    private ForumTopicService service;

    @PostMapping
    public ResponseEntity<ForumTopic> criarTopico(@RequestBody ForumTopic topic) {
        return ResponseEntity.ok(service.salvar(topic));
    }

    @GetMapping
    public ResponseEntity<List<ForumTopic>> listarTopicos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}