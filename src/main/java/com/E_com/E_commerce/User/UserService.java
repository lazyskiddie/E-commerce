package com.E_com.E_commerce.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    // it will create the constructor using autowired
    @Autowired
    private UserRepository userRepository;

    // it wiil find the user present in database and send it to the controllers
    public List<User> getalluser(){
        return userRepository.findAll();
    }

    // it will take data from the controller and send to the repository
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // this is for the user to fetch from the user "id"
    public User finduserbyid(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found!!"));
    }
}
