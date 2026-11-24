package com.ecom.UserService.dto;

import com.ecom.UserService.model.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private UserRole role;
    private Addressdto address;
}
