package com.bni.report.service;

import com.bni.report.entities.User;
import com.bni.report.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByName(String name){
        return userRepository.findByName(name);
    }
}
