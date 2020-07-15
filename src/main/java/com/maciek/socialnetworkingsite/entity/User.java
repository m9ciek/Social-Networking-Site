package com.maciek.socialnetworkingsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    @JsonIgnore
    private String password;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userId", updatable = false, insertable = false)
    private List<Post> posts;

}
