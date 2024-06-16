package com.app.blogrestapi.controller;

import com.app.blogrestapi.dto.LikeDTO;
import com.app.blogrestapi.model.Like;
import com.app.blogrestapi.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/")
public class LikeController {

    private final LikeService likeService;
    @Autowired
    public LikeController(LikeService likeService){
        this.likeService = likeService;
    }

    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<LikeDTO> createLike(@PathVariable(value = "postId") Long postId, @Valid @RequestBody LikeDTO likeDTO){
        LikeDTO createdLike = likeService.createLike(postId, likeDTO);

        return new ResponseEntity<>(createdLike, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/likes")
    public ResponseEntity<List<LikeDTO>> getLikeByPostId(@PathVariable("postId") Long postId){
        List<LikeDTO> getLike = likeService.getLikeByPostId(postId);
        return new ResponseEntity<>(getLike, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/likes/{likesId}")
    public ResponseEntity<LikeDTO> getLikeById(@PathVariable("postId") Long postId, @PathVariable("likesId") Long likeId){
        LikeDTO getLikeById = likeService.getLikeById(postId, likeId);
        return ResponseEntity.ok(getLikeById);
    }

    @PutMapping("/posts/{postId}/likes/{likesId}")
    public ResponseEntity<LikeDTO> updateLike(@PathVariable("postId") Long postId, @PathVariable("likesId") Long likesId, @Valid @RequestBody LikeDTO likeDTO){
        LikeDTO newLike = likeService.updateLike(postId, likesId, likeDTO);
        return new ResponseEntity<>(newLike, HttpStatus.CREATED);
    }
}
