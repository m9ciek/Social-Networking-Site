package com.maciek.socialnetworkingsite.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
