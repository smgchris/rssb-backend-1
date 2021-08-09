package com.rssb.backend1.controller;

import com.rssb.backend1.entity.User;
import com.rssb.backend1.entity.UserRole;
import com.rssb.backend1.models.AuthenticationRequest;
import com.rssb.backend1.models.AuthenticationResponse;
import com.rssb.backend1.models.UserDto;
import com.rssb.backend1.service.UserRoleService;
import com.rssb.backend1.service.UserServiceImpl;
import com.rssb.backend1.util.JwtUtil;
import com.rssb.backend1.util.Msg;
import com.rssb.backend1.util.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserServiceImpl userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping(value = "/auth",method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:8080")
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

            List<User> users = userService.findAll();

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

            List<UserRole> roles = userRoleService.findAll();

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
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) throws Exception{
        ResponseType response=new ResponseType();

        try {
            System.out.println(userDto.getPassword());
            User user = userService.createUser(userDto);

            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("user created successfully");
            response.setObject(user);

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
    public ResponseEntity<Object> updateUser(@RequestBody UserDto userDto) throws Exception{
        ResponseType response=new ResponseType();

        try {
            User user = userService.updateUser(userDto);

            if(user !=null) {
                response.setCode(Msg.SUCCESS_CODE);
                response.setDescription("user updated successfully");
                response.setObject(user);
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
