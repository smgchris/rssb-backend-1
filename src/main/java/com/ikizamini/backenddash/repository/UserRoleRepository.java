package com.ikizamini.backenddash.repository;

import com.ikizamini.backenddash.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository <UserRole, Integer>{

   List<UserRole> findAll();

}
