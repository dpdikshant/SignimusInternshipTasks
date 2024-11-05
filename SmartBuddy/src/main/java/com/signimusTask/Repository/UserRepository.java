package com.signimusTask.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.signimusTask.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Method to find a user by username
    User findByUsername(String username);
}
