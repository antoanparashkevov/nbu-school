package org.example.gradingcenter.web.view.controller;

import org.springframework.ui.Model;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> allUsers = userService.getAllUsers(); // Replace with your logic
        model.addAttribute("users", allUsers);
        return "admin"; // maps to admin.html
    }
}

