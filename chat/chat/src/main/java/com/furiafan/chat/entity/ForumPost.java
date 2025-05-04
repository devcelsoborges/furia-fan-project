package com.furiafan.chat.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ForumPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String autor;
    private String mensagem;
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private ForumTopic topico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public ForumTopic getTopico() {
        return topico;
    }

    public void setTopico(ForumTopic topico) {
        this.topico = topico;
    }
}

