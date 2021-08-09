package com.rssb.backend1.service;

import com.rssb.backend1.entity.UserRole;
import com.rssb.backend1.models.UserDto;
import com.rssb.backend1.repository.UserRepository;
import com.rssb.backend1.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;

    private HttpServletRequest request;



    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public User findById(Integer id)
    {
        User user;
        user = userRepository.findById(id).get();
        return user;
    }

    @Override
    public List<User> findAll()
    {
        return userRepository.findAll();
    }

    @Override
    public User register(User user)
    {
        //register the phone for the first time
        try
        {
            user.setRole(userRoleService.findById(2)); // 2 represents the id of ROLE_USER
            user.setStatus(0);
            user.setUsername(user.getUsername());
            return userRepository.save(user);
        }catch (RuntimeException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public User createUser(UserDto userDto)
    {

        User user = new User();

        user.setName(userDto.getName());
        user.setUsername(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userDto.getRole() == null)
        {
            user.setRole(userRoleService.findById(2)); // 2 represents the id of ROLE_USER
        }
        else
        {
            user.setRole(userRoleService.findById(Integer.parseInt(userDto.getRole())));
        }

        user.setStatus(0);


        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDto userDto)
    {

        User existing = userRepository.findByUsername(userDto.getUsername());
        if(existing!=null){
            if(userDto.getName()!=""&& userDto.getName()!=null)
                existing.setName(userDto.getName());
            if(userDto.getPassword()!=""&& userDto.getPassword()!=null&& userDto.getPassword().length()>5)
                existing.setPassword(passwordEncoder.encode(userDto.getPassword()));
            if(userDto.getRole()!=""&& userDto.getRole()!=null)
                existing.setRole(userRoleService.findById(Integer.parseInt(userDto.getRole())));
            if(userDto.getStatus()!=""&& userDto.getStatus()!=null)
                existing.setStatus(Integer.parseInt(userDto.getStatus()));

            return userRepository.save(existing);
        }else
            return null;


    }


    @Override
    public Page<User> findByPaging(Integer pageNo, Integer pageSize)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id"));

        Page<User> pagedResult = userRepository.findAll(paging);

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
    public User findByUsernamePwd(String user2name, String password)
    {
        User user = userRepository.findByUsername(user2name);
        String encodedPwd = passwordEncoder.encode(password);

        if (user.getPassword().equals(encodedPwd))
        {
            return user;
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

        User user = userRepository.findByUsername(username);

        if (user == null)
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }


        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(Collections.singletonList(user.getRole())));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<UserRole> roles)
    {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    // @Override
    public User findByUsername(String user2name)
    {
        return userRepository.findByUsername(user2name);
    }

}
