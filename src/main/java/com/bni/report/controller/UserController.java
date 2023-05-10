package com.bni.report.controller;

import com.bni.report.entities.Kelompok;
import com.bni.report.entities.User;
import com.bni.report.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage(Model model){
        model.addAttribute("account", new User());
        model.addAttribute("kelompok", new Kelompok());

        return "admin";
    }

    @PostMapping("/addUser")
    public String add(User user){
        userService.save(user);
        return "admin";
    }

}
