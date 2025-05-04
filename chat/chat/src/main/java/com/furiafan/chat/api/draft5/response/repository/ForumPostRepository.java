package com.furiafan.chat.api.draft5.response.repository;

import com.furiafan.chat.entity.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
}

