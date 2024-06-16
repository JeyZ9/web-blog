package com.app.blogrestapi.controller;

import com.app.blogrestapi.dto.PostDTO;
import com.app.blogrestapi.model.PostResponse;
import com.app.blogrestapi.service.PostService;
import com.app.blogrestapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/posts")
@Tag(name = "CRUD REST APIs for Post Resource")
public class PostController {

    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    @SecurityRequirement(name = "Bear Authentication")
    @Operation(summary = "Create post REST API")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        PostDTO createdDTO = postService.createPost(postDTO);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary = "Get All Post REST API", description = "Get All Post REST API is used to get all post in the database")
    @ApiResponse(responseCode = "201", description = "Http Status 201 OK")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                   @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                   @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                   @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        PostResponse getAllPosts = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(getAllPosts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Post By ID REST API", description = "Get Post By ID REST API is used to get post by id from the database")
    @ApiResponse(responseCode = "201", description = "Http Status 201 OK")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id){
        PostDTO getPostById = postService.getPostById(id);
        return ResponseEntity.ok(getPostById);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bear Authentication")
    @Operation(summary = "Update Post REST API", description = "Update Post REST API is used to update post already save in the database")
    @ApiResponse(responseCode = "201", description = "Http Status 201 OK")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable("id") long id){
        PostDTO postResponse = postService.updatePost(postDTO, id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bear Authentication")
    @Operation(summary = "Delete Post REST API", description = "Delete Post REST API is used to delete post from the database")
    @ApiResponse(responseCode = "201", description = "Http Status 201 OK")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post successfully deleted", HttpStatus.OK);
    }

    // Build Get Posts by Category REST API
    //http://localhost:8080/api/v1/posts/category/1
    @GetMapping("/category/{id}")
    @Operation(summary = "Get Categories REST API", description = "Get Post By Category REST API uesd to get post in the database by their respective Categories")
    @ApiResponse(responseCode = "201", description = "Http Status 201 OK")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable("id") Long categoryId){
        List<PostDTO> postDTOS = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok(postDTOS);
    }

}
