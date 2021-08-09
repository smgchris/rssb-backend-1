package com.rssb.backend1.repository;

import com.rssb.backend1.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    User findByEmail(String email);
    User findByUsername(String username);
    List<User> findAll();
    @Override
    Page<User> findAll(Pageable pageable);


}