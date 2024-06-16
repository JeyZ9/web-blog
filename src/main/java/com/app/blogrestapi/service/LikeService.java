package com.app.blogrestapi.service;

import com.app.blogrestapi.dto.LikeDTO;

import java.util.List;

public interface LikeService {

    LikeDTO createLike(Long postId, LikeDTO likeDTO);

    List<LikeDTO> getLikeByPostId(Long postId);

    LikeDTO getLikeById(Long postId, Long likeId);

    LikeDTO updateLike(Long postId, Long likeId, LikeDTO likeRequest);
}
