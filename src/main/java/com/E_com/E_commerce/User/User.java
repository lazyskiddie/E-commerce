package com.E_com.E_commerce.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "User Data for E-Comm")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Please Enter the user name!!")
    private String username;
    @NotNull(message = "Please Enter the password!!")
    private String password;
    @NotNull(message = "Please Enter the email!!")
    private String email;
}
