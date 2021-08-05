package com.ikizamini.backenddash.controller;

import com.ikizamini.backenddash.entity.User;
import com.ikizamini.backenddash.entity.User2;
import com.ikizamini.backenddash.models.AuthenticationRequest;
import com.ikizamini.backenddash.models.AuthenticationResponse;
import com.ikizamini.backenddash.models.PagingDto;
import com.ikizamini.backenddash.models.UserDto;
import com.ikizamini.backenddash.service.User2ServiceImpl;
import com.ikizamini.backenddash.service.UserService;
import com.ikizamini.backenddash.util.JwtUtil;
import com.ikizamini.backenddash.util.Msg;
import com.ikizamini.backenddash.util.ResponseType;
import com.ikizamini.backenddash.util.UsersResponseType;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private User2ServiceImpl user2Service;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping({ "/hello" })
    public String firstPage() {
        return "Hello World";
    }

    //get users by paging
    @RequestMapping(value = "/f-users",method = RequestMethod.POST)
    @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<Object> getFusers(@RequestBody PagingDto pagingDto) throws Exception{
        UsersResponseType response=new UsersResponseType();

        try {

            Page<User> pagedResult = userService.findByPaging(pagingDto.getPage(), pagingDto.getSize());
            List<User> users = pagedResult.getContent();
            int totalPages = pagedResult.getTotalPages();
            long numberOfUsers = pagedResult.getTotalElements();

            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("users retrieved successfully");
            response.setObject(users);
            response.setNumberOfPages(totalPages);
            response.setNumberOfUsers(numberOfUsers);
        }
        catch (RuntimeException e) {
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("couldn't retrieve the users");
            response.setObject(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //get users by paging
    @RequestMapping(value = "/f-user",method = RequestMethod.POST)
    @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<Object> getFuser(@RequestBody String id) throws Exception{
        ResponseType response=new ResponseType();

        try {

            User user= userService.findByUserId(Integer.parseInt(id));

            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("user retrieved successfully");
            response.setObject(user);
            
        }
        catch (RuntimeException e) {
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("couldn't retrieve the user");
            response.setObject(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //get all users from the database
    @RequestMapping(value = "/f-all-users",method = RequestMethod.POST)
    @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<Object> getAllusers() throws Exception{
        ResponseType response=new ResponseType();

        try {

            List<User> users=userService.findAll();

            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("users retrieved successfully");
            response.setObject(users);

        }catch (RuntimeException e) {
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("couldn't retrieve the users");
            response.setObject(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //update a user
    @RequestMapping(value = "/u-customer",method = RequestMethod.POST)
    @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<Object> updateCustomer(@RequestBody UserDto userDto) throws Exception{
        ResponseType response=new ResponseType();

        try {
            System.out.println(userDto.getPacks());
            User user = userService.updateUser(userDto);

            if(user!=null) {
                response.setCode(Msg.SUCCESS_CODE);
                response.setDescription("customer updated successfully");
                response.setObject(user);
            }else{
                response.setCode(Msg.ERROR_CODE);
                response.setDescription("customer doesn't exist");
                response.setObject(null);
            }

        }
        catch (RuntimeException e) {
            e.printStackTrace();
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("couldn't update the user");
            response.setObject(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
