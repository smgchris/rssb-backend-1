package com.ikizamini.backenddash.service;

import com.ikizamini.backenddash.entity.UserRole;
import com.ikizamini.backenddash.models.UserRoleDto;

import java.util.List;


public interface UserRoleService
{


     UserRole findById(Integer id);
     List<UserRole> findAll();
     UserRole save(UserRoleDto userRoleDto);
     UserRole updateStatus(Integer userRoleId);
     UserRole update(UserRole userRole);
    // List<UserRole> findByPaging(Integer pageNo, Integer pageSize);


}
