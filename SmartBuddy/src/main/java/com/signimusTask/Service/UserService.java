package com.signimusTask.Service;

import java.util.Collection;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.signimusTask.Repository.UserRepository;
import com.signimusTask.entity.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String password, Collection<String> roles) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, roles);
        return userRepository.save(user);
    }
}

