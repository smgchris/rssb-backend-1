package com.rssb.backend1.service;

import com.rssb.backend1.entity.UserRole;
import com.rssb.backend1.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

;

@Slf4j
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;


    public UserRole findById(Integer id)  {
        UserRole userRole;
        userRole = userRoleRepository.findById(id).get();
        System.out.println("user2Role"+ userRole.getRole());
        return userRole;
    }

    @Override
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public UserRole update(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole create(UserRole userRole) {
        try{
            return userRoleRepository.save(userRole);
        }
        catch (RuntimeException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserRole updateStatus(Integer user2RoleId) {
        UserRole userRole1 = userRoleRepository.findById(user2RoleId).get();

        if(userRole1 ==null)
            return null;
        else
            userRole1.setStatus(1);

        return userRoleRepository.save(userRole1);
    }




}
