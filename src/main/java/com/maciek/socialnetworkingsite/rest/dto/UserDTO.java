package com.maciek.socialnetworkingsite.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
public class UserDTO {

    private long id;

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please enter correct email")
    private String email;

    @JsonIgnore
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 16, message = "Password must be between 6 and 16 characters")
    private String password;

}
