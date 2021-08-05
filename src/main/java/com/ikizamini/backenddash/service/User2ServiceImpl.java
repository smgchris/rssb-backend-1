package com.ikizamini.backenddash.service;

import com.ikizamini.backenddash.entity.User;
import com.ikizamini.backenddash.entity.User2Role;
import com.ikizamini.backenddash.models.User2Dto;
import com.ikizamini.backenddash.repository.User2Repository;
import com.ikizamini.backenddash.entity.User2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class User2ServiceImpl implements User2Service
{

    @Autowired
    private User2Repository user2Repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private com.ikizamini.backenddash.service.User2RoleService user2RoleService;

    private HttpServletRequest request;



    public User2 findByEmail(String email)
    {
        System.out.println("hello");
        return user2Repository.findByEmail(email);
    }

    public User2 findById(Integer id)
    {
        User2   user2;
        user2 = user2Repository.findById(id).get();
        return user2;
    }

    @Override
    public List<User2> findAll()
    {
        return user2Repository.findAll();
    }

    @Override
    public User2 register(User2 user2)
    {
        //register the phone for the first time
        try
        {
            user2.setRole(user2RoleService.findById(2)); // 2 represents the id of ROLE_USER
            user2.setStatus(0);
            user2.setUsername(user2.getUsername());
            user2.setLoginTrials(0);
            return user2Repository.save(user2);
        }catch (RuntimeException e)
        {
            e.printStackTrace();
            return null;
        }

    }

//    @Override
//    public User2 registerBySocial(User2 user2){
//        //register the phone for the first time
//
//        user2.setRoles(Arrays.asList(user2RoleService.findById(3))); // 3 represents the id of ROLE_USER
//        user2.setStatus(0);
//        user2.setUsername(user2.getUsername());
//
//        return user2Repository.save(user2);
//    }

    @Override
    public void createPasscode(String user2name, String code)
    {
        User2 user2 = findByUsername(user2name);
        user2.setPassword(passwordEncoder.encode(code));
        user2Repository.save(user2);
    }

    @Override
    public void createNickname(String user2name, String nickname)
    {
        User2 user2 = findByUsername(user2name);
        user2.setFirstName(nickname);
        user2Repository.save(user2);
    }

    @Override
    public User2 createUser(User2Dto user2Dto)
    {

        User2 user2 = new User2();

        user2.setFirstName(user2Dto.getFirstname());
        user2.setLastName(user2Dto.getLastname());
        user2.setUsername(user2Dto.getPhone());
        user2.setEmail(user2Dto.getEmail());
        user2.setPhone(user2Dto.getPhone());
        user2.setPassword(passwordEncoder.encode(user2Dto.getPassword()));
        //user2.setPassword(user2Dto.getPassword());
        if (user2Dto.getRole() == null)
        {
            user2.setRole(user2RoleService.findById(2)); // 2 represents the id of ROLE_USER
        }
        else
        {
            user2.setRole(user2RoleService.findById(Integer.parseInt(user2Dto.getRole())));
        }

        user2.setStatus(0);


        return user2Repository.save(user2);
    }

    @Override
    public User2 updateUser(User2Dto user2Dto)
    {

        User2 existing = user2Repository.findByUsername(user2Dto.getUsername());
        if(existing!=null){
            if(user2Dto.getFirstname()!=""&&user2Dto.getFirstname()!=null)
                existing.setFirstName(user2Dto.getFirstname());
            if(user2Dto.getLastname()!=""&&user2Dto.getLastname()!=null)
                existing.setLastName(user2Dto.getLastname());
            if(user2Dto.getPassword()!=""&&user2Dto.getPassword()!=null&&user2Dto.getPassword().length()>5)
                existing.setPassword(passwordEncoder.encode(user2Dto.getPassword()));
            if(user2Dto.getRole()!=""&&user2Dto.getRole()!=null)
                existing.setRole(user2RoleService.findById(Integer.parseInt(user2Dto.getRole())));
            if(user2Dto.getStatus()!=""&&user2Dto.getStatus()!=null)
                existing.setStatus(Integer.parseInt(user2Dto.getStatus()));

            return user2Repository.save(existing);
        }else
            return null;




    }

//    @Override
//    public User2 profileEdit(UserDto1 user2Dto, Integer id)
//    {
//
//        User2 existing = findById(id);
//
//
//        existing.setFirstName(user2Dto.getNickname());
//        existing.setPhone(user2Dto.getPhone().replace(" ", ""));
//        existing.setEmail(user2Dto.getEmail());
//        existing.setLocation(user2Dto.getLocation());
//        if (user2Dto.getProfile() != null)
//        {
//            existing.setProfilePhoto(user2Dto.getProfile());
//        }
//        if (user2Dto.getCover() != null)
//        {
//            existing.setCoverPhoto(user2Dto.getCover());
//        }
//
//        return user2Repository.save(existing);
//    }

//    @Override
//    public User2 updateStatus(Integer user2Id)
//    {
//        User2 user21 = user2Repository.findById(user2Id).get();
//        System.out.println("gigi" + user21.getEmail());
//
//
//        user21.setStatus(1);
//        return user2Repository.save(user21);
//    }


    @Override
    public Page<User2> findByPaging(Integer pageNo, Integer pageSize)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("userId"));

        Page<User2> pagedResult = user2Repository.findAll(paging);

        if (pagedResult.hasContent())
        {
            return pagedResult;
        }
        else
        {
            return null;
        }
    }

    @Override
    public User2 findByUsernamePwd(String user2name, String password)
    {
        User2 user2 = user2Repository.findByUsername(user2name);
        String encodedPwd = passwordEncoder.encode(password);

        if (user2.getPassword().equals(encodedPwd))
        {
            return user2;
        }
        else
        {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

        username = username.replace(" ", "");

        User2 user = user2Repository.findByUsername(username);

        if (user == null)
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

//        if(user2.getLoginTrials()>3)
//        {
//            throw new RuntimeException("blocked");
//        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(Arrays.asList(user.getRole())));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<User2Role> roles)
    {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    // @Override
    public User2 findByUsername(String user2name)
    {
        return user2Repository.findByUsername(user2name);
    }

//    @Override
//    public User2 updateUser(User2 user2)
//    {
//        try
//        {
//           return user2Repository.save(user2);
//        } catch (RuntimeException e)
//        {
//            return null;
//        }
//    }

//    @Override
//    public User2 currentUser()
//    {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User2 user2;
//        if (principal instanceof UserDetails)
//        {
//
//            String user2name = ((UserDetails) principal).getUsername();
//            user2 = findByUsername(user2name);
//        }
//        else
//        {
//            String user2name = principal.toString();
//            user2 = findByUsername(user2name);
//        }
//
//        return user2;
//    }

//    private String getClientIP() {
//        String xfHeader = request.getHeader("X-Forwarded-For");
//        if (xfHeader == null){
//            return request.getRemoteAddr();
//        }
//        return xfHeader.split(",")[0];
//    }


}
