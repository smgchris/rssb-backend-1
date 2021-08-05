package com.ikizamini.backenddash.service;

import com.ikizamini.backenddash.entity.User2Role;


import java.util.List;


public interface User2RoleService
{


     User2Role findById(Integer id);
     List<User2Role> findAll();
     User2Role updateStatus(Integer userRoleId);
     User2Role update(User2Role userRole);
    // List<UserRole> findByPaging(Integer pageNo, Integer pageSize);


}
