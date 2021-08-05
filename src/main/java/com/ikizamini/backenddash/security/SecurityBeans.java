package com.ikizamini.backenddash.security;

import com.ikizamini.backenddash.service.User2Service;
import com.ikizamini.backenddash.service.User2ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityBeans
{
    @Autowired
    private User2Service userService;




    @Bean
    public PasswordEncoder getEncoder()
    {
        return  new BCryptPasswordEncoder();

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(new BCryptPasswordEncoder());
        return auth;
    }






}
