package com.app.blogrestapi.repository;

import com.app.blogrestapi.model.Category;
import com.app.blogrestapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory(Category category);
}
