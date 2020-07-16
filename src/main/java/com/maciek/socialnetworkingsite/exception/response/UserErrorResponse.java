package com.maciek.socialnetworkingsite.exception.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserErrorResponse {

    private int status;
    private List<String> errors;
    private Date date;

}
