/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectHbase.javaHbase.controller;

import com.projectHbase.javaHbase.User;
import com.projectHbase.javaHbase.UserRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BlackAngel
 */
@RestController
public class UserController {
    
    UserRepository userRepository=new UserRepository();

    
    @PostMapping("/")
    public void addUser(@RequestBody User user) throws IOException
    {
        userRepository.save(user);
    }
    
    public UserRepository getUserRepository() {
        return  userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    
    
}
