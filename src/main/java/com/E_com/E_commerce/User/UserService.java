package com.E_com.E_commerce.User;

import com.E_com.E_commerce.User.dto.Addressdto;
import com.E_com.E_commerce.User.dto.UserRequest;
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
    public User addUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);
        return userRepository.save(user);
    }

    // this is for the user to fetch from the user "id"
    public Optional<UserResponse> finduserbyid(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    // this will get the data for the user and find by user id and update the data of that user
    public boolean updateuser(Long id, UserRequest updateUserRequest) {
        return userRepository.findById(id).map(existingUser -> {
            updateUserFromRequest(existingUser, updateUserRequest);
            userRepository.save(existingUser);
            return true;
        }).orElse(false);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());

        if(userRequest.getAddress() != null) {
            Address address = new Address();
            address.setHouseNo(userRequest.getAddress().getHouseNo());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipCode(userRequest.getAddress().getZipCode());
            user.setAddress(address);
        }
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
