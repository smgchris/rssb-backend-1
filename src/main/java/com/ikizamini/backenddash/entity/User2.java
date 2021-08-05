package com.ikizamini.backenddash.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;


@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"), name = "users3")
public class User2 {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id")
    private Integer id;
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
    private Integer status; //status is 0 by default, 1 if deleted
    @Column(name = "phone")
    private String phone;
    @Column(name = "login_trials",columnDefinition = "integer default 0")
    private Integer loginTrials;
    @Column(name = "first_visit",columnDefinition = "integer default 0")
    private Integer firstVisit;




    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private User2Role  role;


    public User2() {
    }

}
