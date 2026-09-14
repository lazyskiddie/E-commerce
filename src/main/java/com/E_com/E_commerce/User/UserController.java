package com.E_com.E_commerce.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<User> createUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id){
        userService.finduserbyid(id);
        return ResponseEntity.ok(userService.finduserbyid(id));
    }

    @PutMapping("api/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User Updateduser){
        boolean isUpdated = userService.updateuser(id, Updateduser);

        if (isUpdated) {
            return ResponseEntity.ok(Updateduser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
