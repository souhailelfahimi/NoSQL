package com.projectHbase.javaHbase.controller;

import com.projectHbase.javaHbase.Reponse;
import com.projectHbase.javaHbase.ReponseRepository;
import com.projectHbase.javaHbase.User;
import java.io.IOException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReponseController {
    
    ReponseRepository repository=new ReponseRepository();
  @PostMapping("/")
    public void addUser(@RequestBody Reponse reponse) throws IOException {
        repository.save(reponse);

    }

}
