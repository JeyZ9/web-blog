package com.app.blogrestapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LikeDTO {
    private Long id;
    private Boolean isLiked;
}
