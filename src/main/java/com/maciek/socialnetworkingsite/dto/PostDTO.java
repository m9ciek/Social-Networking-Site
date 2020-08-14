package com.maciek.socialnetworkingsite.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDTO {
    private Long id;
    private String body;
    private LocalDateTime created;
    private String imageURL;
}
