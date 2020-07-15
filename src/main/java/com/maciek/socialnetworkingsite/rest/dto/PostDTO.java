package com.maciek.socialnetworkingsite.rest.dto;

import com.maciek.socialnetworkingsite.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostDTO {
    private Long id;
    private String body;
    private LocalDateTime created;
    private String imageURL;
    private List<Comment> comments;
}
