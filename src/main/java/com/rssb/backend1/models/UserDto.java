package com.rssb.backend1.models;

//import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto // this is the userDto that modifies info from the user page
{

//    @NotEmpty
    private String username;

    private String name;

    private String phone;

    private String email;

    private String role;

    private String password;

    private String status;


}
