package com.ikizamini.backenddash.service;

import com.ikizamini.backenddash.entity.User2;
import com.ikizamini.backenddash.models.User2Dto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface User2Service extends UserDetailsService
{

     User2 findByEmail(String email);
     User2 findById (Integer id);
     List<User2> findAll();
     User2 createUser(User2Dto userDto);
     User2 register(User2 user);
     void createPasscode(String username, String code);
     void createNickname(String username, String nickname);
//     User2 updateStatus(Integer userId);
    Page<User2> findByPaging(Integer pageNo, Integer pageSize);
    User2 findByUsernamePwd(String username, String password);
    User2 findByUsername(String username);
//    User2 update(UserDto userDto, Integer id);
//    User2 currentUser();
//    User2 profileEdit(UserDto1 userDto, Integer id);
      User2 updateUser(User2Dto user2Dto);
}
