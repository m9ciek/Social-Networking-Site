package com.maciek.socialnetworkingsite.rest.dto.mapper;

import com.maciek.socialnetworkingsite.entity.Comment;
import com.maciek.socialnetworkingsite.rest.dto.CommentDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CommentDTOMapper {

    private CommentDTOMapper() {
    }

    public static List<CommentDTO> mapCommentsToDTOs(List<Comment> comments) {
        return comments.stream()
                .map(CommentDTOMapper::mapCommentToDTO)
                .collect(Collectors.toList());
    }

    public static CommentDTO mapCommentToDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .created(comment.getCreated())
                .build();
    }

    public static Comment mapDTOToComment(CommentDTO commentDTO) {
        return Comment.builder()
                .id(commentDTO.getId())
                .content(commentDTO.getContent())
                .created(commentDTO.getCreated())
                .build();
    }
}
