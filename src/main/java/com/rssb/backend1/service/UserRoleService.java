package com.rssb.backend1.service;

import com.rssb.backend1.entity.UserRole;


import java.util.List;


public interface UserRoleService
{


     UserRole findById(Integer id);
     List<UserRole> findAll();
     UserRole updateStatus(Integer userRoleId);
     UserRole update(UserRole userRole);
     UserRole create(UserRole userRole);


}
