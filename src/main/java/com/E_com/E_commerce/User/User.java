package com.E_com.E_commerce.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "User_Data_for_E-Comm")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // it will generate the unique id foe each customer
    private Long id;
    // uername for the customer
    @NotNull(message = "Please Enter the user name!!")
    private String username;

    @NotNull(message = "Please Enter the password!!")
    private String password;
    @NotNull(message = "Please Enter the email!!")
    private String email;
    // it is the also a type of identifier for three class = "CUSTOMER", "MERCHANT", "ADMIN"
    private UserRole role = UserRole.Customer;

    // this make sure that if the user is deleted the address must also deleted
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

}
