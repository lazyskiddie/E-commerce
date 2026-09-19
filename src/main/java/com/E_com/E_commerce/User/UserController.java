package com.E_com.E_commerce.User;

import com.E_com.E_commerce.User.dto.UserRequest;
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
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return ResponseEntity.ok().body("Success user added");
    }

    @GetMapping("/api/user/{id}")
    public Optional<UserResponse> getUser(@PathVariable("id") Long id){
        return userService.finduserbyid(id);
    }

    @PutMapping("api/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody UserRequest updateUserRequest){
        boolean isUpdated = userService.updateuser(id, updateUserRequest);

        if (isUpdated) {
            return ResponseEntity.ok("Updated user Successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
