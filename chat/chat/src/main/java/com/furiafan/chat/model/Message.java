package com.furiafan.chat.model;

import java.util.UUID;

public class Message {
    private UUID id;
    private String username;
    private String content;

    public Message() {
        this.id = UUID.randomUUID();
    }

    public Message(String username, String content) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
