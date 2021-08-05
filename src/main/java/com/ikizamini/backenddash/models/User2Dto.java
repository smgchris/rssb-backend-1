package com.ikizamini.backenddash.models;

//import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class User2Dto // this is the userDto that modifies info from the user page
{

//    @NotEmpty
    private String username;

    private String firstname;

    private String lastname;

    private String phone;

    private String email;

    private String role;

    private String password;

    private String status;


}
