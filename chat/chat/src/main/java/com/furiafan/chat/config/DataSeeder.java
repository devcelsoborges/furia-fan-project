package com.furiafan.chat.config;

import com.furiafan.chat.api.draft5.response.repository.ForumPostRepository;
import com.furiafan.chat.api.draft5.response.repository.ForumTopicRepository;
import com.furiafan.chat.entity.ForumPost;
import com.furiafan.chat.entity.ForumTopic;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(ForumTopicRepository topicRepo, ForumPostRepository postRepo) {
        return args -> {
            ForumTopic topico1 = new ForumTopic();
            topico1.setTitulo("Informações Importantes");
            topico1.setDescricao("Aprenda aqui as regras do fórum e lembre-se sempre de segui-las.");

            ForumTopic topico2 = new ForumTopic();
            topico2.setTitulo("Notícias e Novidades");
            topico2.setDescricao("Acompanhe as últimas notícias oficiais da FURIA.");

            topicRepo.saveAll(Arrays.asList(topico1, topico2));

            ForumPost post1 = new ForumPost();
            post1.setAutor("Administrador");
            post1.setMensagem("Bem-vindos ao fórum! Leiam as regras antes de postar.");
            post1.setDataHora(LocalDateTime.now());
            post1.setTopico(topico1);

            ForumPost post2 = new ForumPost();
            post2.setAutor("Fã3");
            post2.setMensagem("A FURIA vai jogar esse final de semana!");
            post2.setDataHora(LocalDateTime.now());
            post2.setTopico(topico2);

            postRepo.saveAll(Arrays.asList(post1, post2));
        };
    }
}

