package com.app.blogrestapi.model;

import com.app.blogrestapi.dto.PostDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostResponse {

    private List<PostDTO> content;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;
}
