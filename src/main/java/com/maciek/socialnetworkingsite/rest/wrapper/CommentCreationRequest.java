package com.maciek.socialnetworkingsite.rest.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentCreationRequest {
    private long postId;
    private long userId;
    private String content;
}
