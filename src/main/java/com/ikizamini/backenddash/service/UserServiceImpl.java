package com.ikizamini.backenddash.service;

import com.ikizamini.backenddash.entity.User;
import com.ikizamini.backenddash.entity.User2;
import com.ikizamini.backenddash.entity.UserRole;
import com.ikizamini.backenddash.models.UserDto;
//import com.ikizamini.backenddash.models.UserDto1;
import com.ikizamini.backenddash.repository.UserRepository;
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
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;
//    @Autowired
//    private LoginAttemptService loginAttemptService;

    private HttpServletRequest request;



    public User findByEmail(String email)
    {
        System.out.println("hello");
        return userRepository.findByEmail(email);
    }

    public User findById(Integer id)
    {
        return userRepository.findById(id).get();
    }

    @Override
    public User findByUserId(Integer id) {
        return userRepository.findByUserId(id);
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
            //user.setRoles(Arrays.asList(userRoleService.findById(2))); // 2 represents the id of ROLE_USER
            user.setStatus(0);
            user.setUsername(user.getUsername());
            user.setBalance(0);
            user.setPacks(0);
            user.setLoginTrials(0);
            return userRepository.save(user);
        }catch (RuntimeException e)
        {
            e.printStackTrace();
            System.out.println("heroku save failed");
            return null;
        }

    }

//    @Override
//    public User registerBySocial(User user){
//        //register the phone for the first time
//
//        user.setRoles(Arrays.asList(userRoleService.findById(3))); // 3 represents the id of ROLE_USER
//        user.setStatus(0);
//        user.setUsername(user.getUsername());
//
//        return userRepository.save(user);
//    }

    @Override
    public void createPasscode(String username, String code)
    {
        User user = findByUsername(username);
        System.out.println("heroku code"+code);
        user.setPassword(passwordEncoder.encode(code));
        userRepository.save(user);
    }

    @Override
    public void createNickname(String username, String nickname)
    {
        User user = findByUsername(username);
        user.setFirstName(nickname);
        userRepository.save(user);
    }

    @Override
    public User save(UserDto userDto)
    {

        User user = new User();

        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastname());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        if (userDto.getRole() == null)
//        {
//            user.setRoles(Arrays.asList(userRoleService.findById(2))); // 2 represents the id of ROLE_USER
//        }
//        else
//        {
//            user.setRoles(Arrays.asList(userRoleService.findById(2)));
//        }

        user.setStatus(0);
        user.setRegNumber("JUSTFOR");


        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDto userDto)
    {

        User existing = userRepository.findByUsername(userDto.getUsername());
        if(existing!=null){
            if(userDto.getFirstname()!=""&&userDto.getFirstname()!=null)
                existing.setFirstName(userDto.getFirstname());
            if(userDto.getPassword()!=""&&userDto.getPassword()!=null&&userDto.getPassword().length()>5)
                existing.setPassword(passwordEncoder.encode(userDto.getPassword()));
            if(userDto.getStatus()!=""&&userDto.getStatus()!=null)
                existing.setStatus(Integer.parseInt(userDto.getStatus()));
            if(userDto.getPacks()!=""&&userDto.getPacks()!=null)
                existing.setPacks(Long.parseLong(userDto.getPacks()));

            return userRepository.save(existing);
        }else
            return null;
    }

//    @Override
//    public User profileEdit(UserDto1 userDto, Integer id)
//    {
//
//        User existing = findById(id);
//
//
//        existing.setFirstName(userDto.getNickname());
//        existing.setPhone(userDto.getPhone().replace(" ", ""));
//        existing.setEmail(userDto.getEmail());
//        existing.setLocation(userDto.getLocation());
//        if (userDto.getProfile() != null)
//        {
//            existing.setProfilePhoto(userDto.getProfile());
//        }
//        if (userDto.getCover() != null)
//        {
//            existing.setCoverPhoto(userDto.getCover());
//        }
//
//        return userRepository.save(existing);
//    }

    @Override
    public User updateStatus(Integer userId)
    {
        User user1 = userRepository.findById(userId).get();

        user1.setStatus(1);
        return userRepository.save(user1);
    }


    @Override
    public Page<User> findByPaging(Integer pageNo, Integer pageSize)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by("userId"));

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
    public User findByUsernamePwd(String username, String password)
    {
        User user = userRepository.findByUsername(username);
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

//        if(user.getLoginTrials()>3)
//        {
//            throw new RuntimeException("blocked");
//        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(new ArrayList<>()));  //is modified
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<UserRole> roles)
    {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getUserRole()))
                .collect(Collectors.toList());
    }

    // @Override
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }


    @Override
    public User currentUser()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user;
        if (principal instanceof UserDetails)
        {

            String username = ((UserDetails) principal).getUsername();
            user = findByUsername(username);
        }
        else
        {
            String username = principal.toString();
            user = findByUsername(username);
        }

        return user;
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }


}
