package com.E_com.E_commerce.User.dto;

import com.E_com.E_commerce.User.Address;
import com.E_com.E_commerce.User.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private UserRole role;
    private Addressdto address;
}
