package com.maciek.socialnetworkingsite.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonManagedReference
    private User user;

    @NonNull
    @Column(name = "body", length = 2000)
    private String body;

    @NonNull
    private LocalDateTime created;

    private String imageURL;

    @OneToMany
    @JoinColumn(name= "postId")
    private List<Comment> comments;
}
