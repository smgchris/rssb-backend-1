package com.rssb.backend1;

import com.rssb.backend1.entity.UserRole;
import com.rssb.backend1.models.UserDto;
import com.rssb.backend1.service.UserRoleService;
import com.rssb.backend1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Component
    public static class Test implements CommandLineRunner {
        @Autowired
        private UserService userService;
        @Autowired
        UserRoleService userRoleService;


        @Override
        public void run(String... args) {
            UserRole userRole=new UserRole("ROLE_ADMIN","admin",0);
            userRoleService.create(userRole);

            UserDto userDto=new UserDto("0788123456","test name","0788123456","email@test.com","1","test123","0");
            userService.createUser(userDto);

        }
    }

}
