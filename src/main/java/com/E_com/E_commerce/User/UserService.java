package com.E_com.E_commerce.User;

import com.E_com.E_commerce.User.dto.Addressdto;
import com.E_com.E_commerce.User.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    // it will create the constructor using autowired
    @Autowired
    private UserRepository userRepository;

    // it wiil find the user present in database and send it to the controllers
    public List<UserResponse> getalluser(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    // it will take data from the controller and send to the repository
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // this is for the user to fetch from the user "id"
    public Optional<UserResponse> finduserbyid(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    // this will get the data for the user and find by user id and update the data of that user
    public boolean updateuser(Long id, User Updateduser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(Updateduser.getUsername());
            existingUser.setPassword(Updateduser.getPassword());
            existingUser.setEmail(Updateduser.getEmail());
            userRepository.save(existingUser);
            return true;
        }).orElse(false);
    }

    private UserResponse mapToUserResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            Addressdto addressDto = new Addressdto();
            addressDto.setHouseNo(user.getAddress().getHouseNo());
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setZipCode(user.getAddress().getZipCode());
            response.setAddress(addressDto);
        }

        return response;
    }
}
