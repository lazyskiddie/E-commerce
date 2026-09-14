package com.E_com.E_commerce.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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

    @GetMapping("/api/user/{id}")
    public User getUser(@PathVariable("id") Long id){
        return userService.finduserbyid(id);
    }

}
