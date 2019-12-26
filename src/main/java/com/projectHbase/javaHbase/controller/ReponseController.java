package com.projectHbase.javaHbase.controller;

import com.projectHbase.javaHbase.bean.Reponse;
import com.projectHbase.javaHbase.repository.ReponseRepository;
import com.projectHbase.javaHbase.bean.User;
import java.io.IOException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/responses")
public class ReponseController {
    
    ReponseRepository repository=new ReponseRepository();
  @PostMapping("/")
    public void addUser(@RequestBody Reponse reponse) throws IOException {
        repository.save(reponse);

    }

}
