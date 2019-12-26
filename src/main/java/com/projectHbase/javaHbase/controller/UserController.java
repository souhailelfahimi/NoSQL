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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BlackAngel
 */
@RestController
//@Controller
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:4200"})

public class UserController {

    UserRepository userRepository = new UserRepository();

//    @PostMapping("/add")
//    public void addUser(@RequestBody User user) throws IOException {
//        userRepository.save(user);
//
//    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addUser(@RequestBody User user) throws IOException {
        userRepository.save(user);

    }

    @GetMapping("/")
    public String index(Model model) {

        return "user";
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) throws IOException {
        return userRepository.findById(id);
    }

    @GetMapping("/all")
    public List<User> findall() throws IOException {
        return userRepository.finAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) throws IOException {
        userRepository.delete(id);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
