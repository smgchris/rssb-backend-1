package com.ikizamini.backenddash.service;

import com.ikizamini.backenddash.entity.UserRole;
import com.ikizamini.backenddash.models.UserRoleDto;
import com.ikizamini.backenddash.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Slf4j
@Service
public class UserRoleServiceImpl implements com.ikizamini.backenddash.service.UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;


    public UserRole findById(Integer id)  {
        System.out.println("hefty");
        UserRole userRole;
        userRole= userRoleRepository.findById(id).get();
        System.out.println("userRole"+userRole.getUserRole());
        return userRole;
    }

    @Override
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public UserRole save(UserRoleDto userRoleDto) {

        UserRole userRole= new UserRole();
        userRole.setUserRole(userRoleDto. getUserRole());
        userRole.setRank(userRoleDto.getRank());
        return userRoleRepository.save(userRole);

    }
    @Override
    public UserRole update(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }
    @Override
    public UserRole updateStatus(Integer userRoleId) {
        UserRole userRole1=userRoleRepository.findById(userRoleId).get();

        if(userRole1==null)
            return null;
        else
            userRole1.setStatus(1);

        return userRoleRepository.save(userRole1);
    }




}
