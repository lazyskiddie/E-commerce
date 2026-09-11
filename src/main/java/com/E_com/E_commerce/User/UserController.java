package com.E_com.E_commerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("api/user")
    public List<User> getallUser(){
        return userService.getalluser();
    }

    @PostMapping("api/user")
    public String createUser(@RequestBody User user){
        userService.addUser(user);
        return "user added successfully";
    }
}
