package com.bni.report.controller;

import com.bni.report.entities.Kelompok;
import com.bni.report.entities.User;
import com.bni.report.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{no}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public String getUsers(
            @PathVariable(value = "no") int currPage,
            @RequestParam(required = false) String keyword,
            Model model
    ){
        int pageSize = 20;
        Page<User> users = null;
        if (keyword == null){
            users = userService.getAll(currPage, pageSize);
        } else {
            users = userService.getAllSearch(currPage, pageSize, keyword);
        }
        List<User> content = users.getContent();

        model.addAttribute("users", content);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", currPage);
        model.addAttribute("totalPages", users.getTotalPages());

        return "userList";
    }

    @GetMapping("/login")
    public String login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public String adminPage(Model model){
        model.addAttribute("user", new User());

        return "/admin";
    }

    @PostMapping("/admin")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public String add(User user){
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String delete(@PathVariable Integer id){
        userService.delete(id);
        return "redirect:/users/1";
    }

}
