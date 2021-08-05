package com.ikizamini.backenddash.models;

//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserDto {


    private String firstname;

    private String lastname;


    private String phone;

    private String username;

    private String email;

    private String password;

    private String packs;

    private String status;

    private Integer role;
    private String profilePhoto;
    private Integer createdBy;
    private Integer classId;
    private String dateTime;



}
