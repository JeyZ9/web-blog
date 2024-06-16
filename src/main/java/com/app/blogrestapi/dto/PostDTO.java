package com.app.blogrestapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.List;

@Getter
@Setter
@Schema(description = "PostDTO model Information")
public class PostDTO {

    private Long id;

    @Schema(description = "Blog Post Title")
    @NotEmpty(message = "post title can't be empty")
    @Size(min = 4, message = "Post title should have at least 3 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    @Schema(description = "Blog Post Description")
    private String description;

    @NotEmpty
    @Schema(description = "Blog Post Content")
    private String content;

    private Set<CommentDTO> comments;

    private List<LikeDTO> likes;

    @Schema(description = "Blog Post Category")
    private Long categoryId;

}
