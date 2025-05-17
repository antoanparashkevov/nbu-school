package org.example.gradingcenter.web.view.controller;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.users.UserOutDto;
import org.example.gradingcenter.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final AuthenticationService authService;

    @GetMapping("/")
    public String getIndex(Model model) {
        /**
         * 1. Take the logged in user
         * 2. Depending on its role, redirect the user to the appropriate page (e.g. student role -> /student)
         * 3. If the role is admin, proceed with the home page that will display a dialog window letting user to choose the wanted view
         */
        UserOutDto user = authService.getLoggedInUser();

        return "index";
    }
}
