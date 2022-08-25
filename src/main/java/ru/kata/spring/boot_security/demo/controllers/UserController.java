package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repos.UserRepository;

import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{user}")
    String showUser(@PathVariable("user") User user, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (user.getUsername().equals(authentication.getName()) ||
                AuthorityUtils.authorityListToSet(authentication.getAuthorities()).contains("ADMIN")) {
            List<User> users = (List<User>) userRepository.findAll();
            model.addAttribute("user", user);
            model.addAttribute("users", users);
            return "user";
        }
        return "error";
    }

}
