package com.ikizamini.backenddash.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="userrole")
public class UserRole
{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="userroleid")
    private Integer userRoleId;
    @Column(name="userrole")
    private String userRole;
    @Column(name="rank")
    private Integer rank;
    @Column(name="status") //deleted or not
    private Integer status;


    public UserRole() {

    }

}
