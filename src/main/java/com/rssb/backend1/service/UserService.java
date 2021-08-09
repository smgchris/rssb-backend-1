package com.rssb.backend1.service;

import com.rssb.backend1.entity.User;
import com.rssb.backend1.models.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService
{

     User findByEmail(String email);
     User findById (Integer id);
     List<User> findAll();
     User createUser(UserDto userDto);
     User register(User user);
     Page<User> findByPaging(Integer pageNo, Integer pageSize);
     User findByUsernamePwd(String username, String password);
     User findByUsername(String username);
     User updateUser(UserDto userDto);
}
