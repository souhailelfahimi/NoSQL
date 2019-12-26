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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

  @GetMapping("/user")
  public String userForm(Model model) {
    model.addAttribute("user", new User());
    return "user";
  }

  @PostMapping("/user")
  public String userSubmit(@ModelAttribute User user) {
    return "result";
  }

}
