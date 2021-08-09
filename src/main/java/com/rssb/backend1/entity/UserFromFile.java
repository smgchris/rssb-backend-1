package com.rssb.backend1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

//represents a user created from the uploaded files of users

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "usersfromfile")
public class UserFromFile {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id")
    private Integer id;
    private String nid;
    private boolean isNidValid;
    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;
    private boolean isEmailValid;
    private String phone;
    private boolean isPhoneValid;
    private String gender;  //expecting more than just male and female
    @Column(name="createdDate")
    private Long createdDate=new Date().getTime();
    @Column(name="createdBy")
    private Integer createdBy;

}
