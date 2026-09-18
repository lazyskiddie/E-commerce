package com.E_com.E_commerce.User;

import com.E_com.E_commerce.User.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("api/user")
    public List<UserResponse> getallUser(){
        return userService.getalluser();
    }

    @PostMapping("api/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/api/user/{id}")
    public Optional<UserResponse> getUser(@PathVariable("id") Long id){
        return userService.finduserbyid(id);
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
