package com.app.blogrestapi.service.impl;

import com.app.blogrestapi.dto.CommentDTO;
import com.app.blogrestapi.exception.BlogAPIException;
import com.app.blogrestapi.exception.ResourceNotFoundException;
import com.app.blogrestapi.model.Comment;
import com.app.blogrestapi.model.Post;
import com.app.blogrestapi.repository.CommentRepository;
import com.app.blogrestapi.repository.PostRepository;
import com.app.blogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    private CommentDTO mapToDTO(Comment comment){
        return modelMapper.map(comment, CommentDTO.class);
    }

    private Comment mapToComment(CommentDTO commentDTO){
        return modelMapper.map(commentDTO, Comment.class);
    }

    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO){

        Comment comment = mapToComment(commentDTO);

        //Retrieve post entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        //Set post to comment entity
        comment.setPost(post);

        // Save comment entity into DB
        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);

    }

    @Override
    public List<CommentDTO> getCommentByPostId(Long postId){

        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId){

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentRequest){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updateComment = commentRepository.save(comment);

        return mapToDTO(updateComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment dose not belong to post");
        }

        commentRepository.delete(comment);
    }
}
