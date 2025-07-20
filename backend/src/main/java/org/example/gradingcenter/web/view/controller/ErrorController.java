package org.example.gradingcenter.web.view.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/access-denied")
    public String getAccessDenied(Model model) {
        model.addAttribute("message", "Access Denied: You do not have permission to view this page.");
        model.addAttribute("status", HttpStatus.FORBIDDEN.value() + " " + HttpStatus.FORBIDDEN.getReasonPhrase());
        return "error"; // This returns your existing error.html template
    }
}
