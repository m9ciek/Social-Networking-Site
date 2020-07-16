package com.maciek.socialnetworkingsite.rest.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostCreationRequest {
    private long userId;
    private String postBody;
}
