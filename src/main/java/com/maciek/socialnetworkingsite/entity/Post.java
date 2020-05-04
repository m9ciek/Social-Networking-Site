package com.maciek.socialnetworkingsite.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String body;

    @NonNull
    private LocalDateTime date;

    private String imageURL;
}
