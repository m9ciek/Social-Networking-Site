package com.maciek.socialnetworkingsite.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDTO {

    private long id;
    private String content;
    private LocalDateTime created;
}
