package com.maciek.socialnetworkingsite.dto.mapper;

import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.dto.PostDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PostDTOMapper {

    private PostDTOMapper() {
    }

    public static List<PostDTO> mapPostsToDTOs(List<Post> posts) {
        return posts.stream()
                .map(PostDTOMapper::mapPostToDTO)
                .collect(Collectors.toList());
    }

    public static PostDTO mapPostToDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .body(post.getBody())
                .imageURL(post.getImageURL())
                .created(post.getCreated())
                .build();
    }

    public static Post mapDTOToPost(PostDTO postDTO) {
        return Post.builder()
                .id(postDTO.getId())
                .id(postDTO.getId())
                .body(postDTO.getBody())
                .imageURL(postDTO.getImageURL())
                .created(postDTO.getCreated())
                .build();
    }
}
