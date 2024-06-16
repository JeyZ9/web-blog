package com.app.blogrestapi.service.impl;

import com.app.blogrestapi.dto.LikeDTO;
import com.app.blogrestapi.exception.BlogAPIException;
import com.app.blogrestapi.exception.ResourceNotFoundException;
import com.app.blogrestapi.model.Like;
import com.app.blogrestapi.model.Post;
import com.app.blogrestapi.repository.LikeRepository;
import com.app.blogrestapi.repository.PostRepository;
import com.app.blogrestapi.service.LikeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, PostRepository postRepository, ModelMapper modelMapper){
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public LikeDTO mapToDTO(Like like){
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(like.getId());
        likeDTO.setIsLiked(like.getIsLiked());
        return likeDTO;
    }

    public Like mapToLike(LikeDTO likeDTO){
        Like like = new Like();
        like.setId(likeDTO.getId());
        like.setIsLiked(Boolean.valueOf(likeDTO.getIsLiked()));
        return like;
    }

    @Override
    public LikeDTO createLike(Long postId, LikeDTO likeDTO){
        Like like = mapToLike(likeDTO);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        like.setPost(post);

        Like newLike = likeRepository.save(like);

        return mapToDTO(newLike);
    }

    @Override
    public List<LikeDTO> getLikeByPostId(Long postId){
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        List<Like> likeList = likeRepository.findByPostId(postId);
        return likeList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public LikeDTO getLikeById(Long postId, Long likeId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Like like = likeRepository.findById(likeId).orElseThrow(() -> new ResourceNotFoundException("Like", "id", likeId));

        if(!like.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Like does not belong to post");
        }

        return mapToDTO(like);
    }

//    @Override
//    public LikeDTO updateLike(Long postId, Long likeId, LikeDTO likeDTO){
//
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
//
//        Like like = likeRepository.findById(likeId)
//                .orElseThrow(() -> new ResourceNotFoundException("Like", "id", likeId));
//
//        if(!like.getPost().getId().equals(post.getId())){
//            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Like does not belong to post");
//        }
//
//        Boolean isLikeValue;
//        if("true".equalsIgnoreCase(likeDTO.getIsLiked())){
//            isLikeValue = true;
//        }else if("false".equalsIgnoreCase(likeDTO.getIsLiked())){
//            isLikeValue = false;
//        }else {
//            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid isLike value: " + likeDTO.getIsLiked());
//        }
//
//        like.setIsLiked(isLikeValue);
//
//        Like updateLike = likeRepository.save(like);
//
//        return mapToDTO(updateLike);
//    }

    @Override
    public LikeDTO updateLike(Long postId, Long likeId, LikeDTO likeDTO){

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new ResourceNotFoundException("Like", "id", likeId));

        if (!like.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Like does not belong to post");
        }

//        System.out.println("Log like value: " + likeDTO.getIsLiked());

        like.setIsLiked(likeDTO.getIsLiked());
        Like updatedLike = likeRepository.save(like);

//        if ("false".equalsIgnoreCase(String.valueOf(likeDTO.getIsLiked()))) {
//            likeRepository.delete(like);
//        }


        return mapToDTO(updatedLike);
    }

}
