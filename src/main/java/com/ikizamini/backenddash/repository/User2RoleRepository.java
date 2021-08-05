package com.ikizamini.backenddash.repository;

import com.ikizamini.backenddash.entity.User2Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User2RoleRepository extends JpaRepository <User2Role, Integer>{

   List<User2Role> findAll();



}
