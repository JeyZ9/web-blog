package com.app.blogrestapi.service;

import com.app.blogrestapi.dto.PostDTO;
import com.app.blogrestapi.model.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDTO getPostById(long id);

    PostDTO updatePost(PostDTO postDTO, long id);

    void deletePostById(long id);

    List<PostDTO> getPostByCategory(Long categoryId);
}
