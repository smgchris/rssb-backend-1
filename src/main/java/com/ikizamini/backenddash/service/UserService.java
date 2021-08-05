package com.ikizamini.backenddash.service;


import com.ikizamini.backenddash.entity.User;
import com.ikizamini.backenddash.models.UserDto;
//import com.ikizamini.backenddash.models.UserDto1;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService  extends UserDetailsService
{

     User findByEmail(String email);
     User findById (Integer id);
     User findByUserId(Integer id);
     List<User> findAll();
     User save(UserDto userDto);
     User register(User user);
     void createPasscode(String username, String code);
     void createNickname(String username, String nickname);
     User updateStatus(Integer userId);
     Page<User> findByPaging(Integer pageNo, Integer pageSize);
    User findByUsernamePwd(String username, String password);
    User findByUsername(String username);
    User updateUser(UserDto userDto);
    User currentUser();
//    User profileEdit(UserDto1 userDto, Integer id);
    //User updateUser(User user);
//    User registerBySocial(User user);
}
