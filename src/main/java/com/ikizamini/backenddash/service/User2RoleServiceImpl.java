package com.ikizamini.backenddash.service;

import com.ikizamini.backenddash.entity.User2Role;
import com.ikizamini.backenddash.repository.User2RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

;

@Slf4j
@Service
public class User2RoleServiceImpl implements User2RoleService {

    @Autowired
    private User2RoleRepository user2RoleRepository;


    public User2Role findById(Integer id)  {
        User2Role user2Role;
        user2Role= user2RoleRepository.findById(id).get();
        System.out.println("user2Role"+user2Role.getRole());
        return user2Role;
    }

    @Override
    public List<User2Role> findAll() {
        return user2RoleRepository.findAll();
    }

//    @Override
//    public User2Role save(User2RoleDto user2RoleDto) {
//
//        User2Role user2Role= new User2Role();
//        user2Role.setUser2Role(user2RoleDto. getUser2Role());
//        user2Role.setRank(user2RoleDto.getRank());
//        return user2RoleRepository.save(user2Role);
//
//    }
    @Override
    public User2Role update(User2Role user2Role) {
        return user2RoleRepository.save(user2Role);
    }
    @Override
    public User2Role updateStatus(Integer user2RoleId) {
        User2Role user2Role1=user2RoleRepository.findById(user2RoleId).get();

        if(user2Role1==null)
            return null;
        else
            user2Role1.setStatus(1);

        return user2RoleRepository.save(user2Role1);
    }




}
