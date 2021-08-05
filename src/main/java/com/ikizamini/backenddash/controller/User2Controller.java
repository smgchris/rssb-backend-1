package com.ikizamini.backenddash.controller;

import com.ikizamini.backenddash.entity.User;
import com.ikizamini.backenddash.entity.User2;
import com.ikizamini.backenddash.entity.User2Role;
import com.ikizamini.backenddash.models.AuthenticationRequest;
import com.ikizamini.backenddash.models.AuthenticationResponse;
import com.ikizamini.backenddash.models.PagingDto;
import com.ikizamini.backenddash.models.User2Dto;
import com.ikizamini.backenddash.service.User2RoleService;
import com.ikizamini.backenddash.service.User2ServiceImpl;
import com.ikizamini.backenddash.util.JwtUtil;
import com.ikizamini.backenddash.util.Msg;
import com.ikizamini.backenddash.util.ResponseType;
import com.ikizamini.backenddash.util.UsersResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class User2Controller {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private User2ServiceImpl userService;
    @Autowired
    private User2RoleService user2RoleService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping(value = "/auth",method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
            );

        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password",e);
        }
        final UserDetails userDetails= userService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt= jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    //gets the users of the dashboard
    @RequestMapping(value = "/p-users",method = RequestMethod.POST)
    @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<Object> getPusers() throws Exception{
        ResponseType response=new ResponseType();

        try {

            List<User2> users = userService.findAll();

            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("users retrieved successfully");
            response.setObject(users);

        }
        catch (RuntimeException e) {
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("couldn't retrieve the users");
            response.setObject(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //get the roles
    @RequestMapping(value = "/p-roles",method = RequestMethod.POST)
    @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<Object> getRoles() throws Exception{
        ResponseType response=new ResponseType();

        try {

            List<User2Role> roles = user2RoleService.findAll();

            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("roles retrieved successfully");
            response.setObject(roles);

        }
        catch (RuntimeException e) {
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("couldn't retrieve the roles");
            response.setObject(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //create new user
    @RequestMapping(value = "/a-user",method = RequestMethod.POST)
    @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<Object> createUser(@RequestBody User2Dto user2Dto) throws Exception{
        ResponseType response=new ResponseType();

        try {
            System.out.println(user2Dto.getPassword());
            User2 user2 = userService.createUser(user2Dto);

            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("user created successfully");
            response.setObject(user2);

        }
        catch (RuntimeException e) {
            e.printStackTrace();
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("couldn't add the user");
            response.setObject(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //update a user
    @RequestMapping(value = "/u-user",method = RequestMethod.POST)
    @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<Object> updateUser(@RequestBody User2Dto user2Dto) throws Exception{
        ResponseType response=new ResponseType();

        try {
            User2 user2 = userService.updateUser(user2Dto);

            if(user2!=null) {
                response.setCode(Msg.SUCCESS_CODE);
                response.setDescription("user updated successfully");
                response.setObject(user2);
            }else{
                response.setCode(Msg.ERROR_CODE);
                response.setDescription("user doesn't exist");
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
