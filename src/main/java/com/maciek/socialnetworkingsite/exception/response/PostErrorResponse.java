package com.maciek.socialnetworkingsite.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostErrorResponse {

    private int status;
    private List<String> errors;
    private LocalDateTime date;
}
