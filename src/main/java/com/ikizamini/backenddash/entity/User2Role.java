package com.ikizamini.backenddash.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="user2role")
public class User2Role
{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id")
    private Integer id;
    @Column(name="role")
    private String role;
    @Column (name="name")
    private String name;
    @Column(name="rank")
    private Integer rank;
    @Column(name="status") //deleted or not
    private Integer status;


    public User2Role() {

    }
}