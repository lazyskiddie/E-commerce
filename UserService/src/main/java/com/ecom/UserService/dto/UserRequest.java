package com.E_com.E_commerce.User.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private Addressdto address;
}
