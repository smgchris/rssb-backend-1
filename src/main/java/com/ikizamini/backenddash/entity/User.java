package com.ikizamini.backenddash.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;


@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"), name = "users2")
public class User {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="user_id")
    private Integer userId;
    private String username;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column(name="email")
    private String email;
    @JsonIgnore
    @Column(name="password")
    private String password;
    @Column(name="createdDate")
    private Date createdDate=new Date();
    @Column(name="createdBy")
    private Integer createdBy;
    @Column(name="profilePhoto")
    private String profilePhoto;
    @Column(name="coverPhoto")
    private String coverPhoto;
    @Column(name="classId")
    private Integer classId;
    private Integer status; //status is 0 by default, 1 if deleted
    @Column(name="reg_number")
    private String  regNumber;
    @Column(name = "phone")
    private String phone;
    @Column(name = "location")
    private String location;
    @Column(name = "balance")
    private Integer balance;
    @Column(name = "packs")
    private long  packs;
    @Column(name = "signup_mode")
    private String  signupMode;
    @Column(name = "login_trials",columnDefinition = "integer default 0")
    private Integer loginTrials;
    @Column(name = "first_visit",columnDefinition = "integer default 0")
    private Integer firstVisit;

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "users_roles",
//            joinColumns = @JoinColumn(
//                    name = "user_id", referencedColumnName = "user_id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "userroleid"))
//    private Collection< UserRole > roles;


    public User() {
    }

}
