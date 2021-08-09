package com.rssb.backend1.models;

//import javax.validation.constraints.NotEmpty;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class UserFromFileDto // this is the userDto that modifies info from the user page
{

    private String nid;
    private String name;
    private String email;
    private String phone;
    private String gender;  //expecting more than just male and female


}
