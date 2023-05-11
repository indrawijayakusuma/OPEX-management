package com.bni.report.service;

import com.bni.report.entities.User;
import com.bni.report.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByName(String name){
        return userRepository.findByName(name);
    }

    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> saveAll(List<User> users){
        return userRepository.saveAll(users);
    }

    public Pageable page(int currPage, int pageSize){
        Pageable pageable = PageRequest.of(currPage - 1, pageSize);
        return pageable;
    }

    public Page<User> getAllSearch(int currPage, int pageSize, String keyword){
        Pageable page = page(currPage, pageSize);
        return userRepository.findByName(keyword, page);
    }

    public Page<User> getAll(int currPage, int pageSize){
        Pageable page = page(currPage, pageSize);
        return userRepository.findAll(page);
    }
}
