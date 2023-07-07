package com.bni.report.Security;

import com.bni.report.entities.User;
import com.bni.report.repositories.UserRepository;
import com.bni.report.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(username);
        return user.map(CustomUserDetail::new).orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
    }
}
