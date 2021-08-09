package com.rssb.backend1.repository;

import com.rssb.backend1.entity.User;
import com.rssb.backend1.entity.UserFromFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFromFileRepository extends JpaRepository<UserFromFile, Integer>
{
    UserFromFile findByEmail(String email);
    List<UserFromFile> findAll();
    @Override
    Page<UserFromFile> findAll(Pageable pageable);


}