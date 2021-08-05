package com.ikizamini.backenddash.repository;

import com.ikizamini.backenddash.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Integer>
{
   User findByEmail(String email);
   User findByUsername(String username);
   //User findByUserName(String username);
   List<User> findAll();

   @Override
   Page<User> findAll(Pageable pageable);
   User findUserByPhoneAndStatus(String phone, int status );
   User findByUserId(Integer id);


}
