package com.rssb.backend1.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="userrole")
public class UserRole {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "role")
    private String role;
    @Column(name = "name")
    private String name;
    @Column(name = "status") //deleted or not
    private Integer status;

    public UserRole(String role, String name, Integer status) {
        this.role = role;
        this.name = name;
        this.status = status;
    }
}