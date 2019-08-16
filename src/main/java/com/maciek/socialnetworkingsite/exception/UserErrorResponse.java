package com.maciek.socialnetworkingsite.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserErrorResponse {

    private int status;
    private String message;
    private Date date;

}
