package com.rssb.backend1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

//represents a system user with privileges on the backend

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"), name = "users2")
public class User {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id")
    private Integer id;
    private String username;
    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;
    @JsonIgnore
    @Column(name="password")
    private String password;
    @Column(name="createdDate")
    private Long createdDate=new Date().getTime();
    @Column(name="createdBy")
    private Integer createdBy;
    private Integer status; //status is 0 by default, 1 if deleted



    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private UserRole role;


    public User() {
    }

}
