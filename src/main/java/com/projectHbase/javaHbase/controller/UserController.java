/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectHbase.javaHbase.controller;

import com.projectHbase.javaHbase.bean.User;
import com.projectHbase.javaHbase.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BlackAngel
 */
@RestController
public class UserController {

    UserRepository userRepository = new UserRepository();

    @PostMapping("/")
    public void addUser(@RequestBody User user) throws IOException {
        userRepository.save(user);

    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) throws IOException {
        return userRepository.findById(id);
    }

    @GetMapping("/users")
    public List<User> findall() throws IOException {
        return userRepository.finAll();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
