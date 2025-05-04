package com.furiafan.chat.controller;

import com.furiafan.chat.model.Match;
import com.furiafan.chat.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/furia")
    public ResponseEntity<List<Match>> getFuriaMatches() {
        try {
            return ResponseEntity.ok(gameService.getRecentMatches());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
