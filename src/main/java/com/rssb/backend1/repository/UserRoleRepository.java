package com.rssb.backend1.repository;

import com.rssb.backend1.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository <UserRole, Integer>{

   List<UserRole> findAll();



}
