package com.furiafan.chat.service;

import com.furiafan.chat.api.draft5.response.repository.ForumTopicRepository;
import com.furiafan.chat.entity.ForumTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForumTopicService {

    @Autowired
    private ForumTopicRepository repository;

    public ForumTopic salvar(ForumTopic topic) {
        topic.setDataPostagem(LocalDateTime.now());
        return repository.save(topic);
    }

    public List<ForumTopic> listarTodos() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "dataPostagem"));
    }
}

