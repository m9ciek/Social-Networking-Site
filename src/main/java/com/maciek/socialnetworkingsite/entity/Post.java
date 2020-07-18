package com.maciek.socialnetworkingsite.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private String body;
    private LocalDateTime created;
    private String imageURL;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "postId", updatable = false, insertable = false)
    private List<Comment> comments;
}
