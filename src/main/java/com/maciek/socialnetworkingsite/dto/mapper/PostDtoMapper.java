package com.maciek.socialnetworkingsite.dto.mapper;

import com.maciek.socialnetworkingsite.dto.PostDTO;
import com.maciek.socialnetworkingsite.entity.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostDtoMapper {

    public PostDtoMapper() {
    }

    public static List<PostDTO> mapToPostDtos(List<Post> posts){
        return posts.stream()
                .map(PostDtoMapper::mapPostToDto)
                .collect(Collectors.toList());
    }

    private static PostDTO mapPostToDto(Post post){
        return PostDTO.builder()
                .id(post.getId())
                .body(post.getBody())
                .imageURL(post.getImageURL())
                .created(post.getCreated())
                .comments(post.getComments())
                .build();
    }
}
