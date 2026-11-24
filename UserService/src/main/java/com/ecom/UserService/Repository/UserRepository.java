package com.ecom.UserService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecom.UserService.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
