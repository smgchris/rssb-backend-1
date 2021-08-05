package com.ikizamini.backenddash.repository;

import com.ikizamini.backenddash.entity.User;
import com.ikizamini.backenddash.entity.User2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User2Repository extends JpaRepository<User2, Integer>
{
    User2 findByEmail(String email);
    User2 findByUsername(String username);
    List<User2> findAll();
    User2 findUserByPhoneAndStatus(String phone,int status );
    @Override
    Page<User2> findAll(Pageable pageable);


}